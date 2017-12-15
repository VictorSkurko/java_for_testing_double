package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Iterator;
import static org.testng.Assert.assertEquals;

public class DeleteContactFromGroupTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withGroupName("NewI")
                    .withGroupHeader("NewI Header").withGroupFooter("NewI Footer"));
        }
        if (app.db().contacts().size() == 0) {
            createContact();
        }
    }

    private ContactData createContact() {
        app.goTo().contactPage();
        ContactData contactData = new ContactData()
                .withFirstName("Владимир")
                .withLastName("Петров")
                .withAddress("Россия, Москва")
                .withHomePhone("945 - 000-00-000")
                .withMobilePhone("800-00-201-6666")
                .withWorkPhone("66-666-6")
                .withEmail("mail@mail.ru");
        app.contact().create(contactData);
        return contactData;
    }

    @Test
    public void testAddContactToGroup(){
        Groups groups = app.db().groups();
        Contacts contacts = app.db().contacts();
        GroupData group = null;
        ContactData contact;
        boolean foundContactInGroup = false;
        Iterator<ContactData> contactsIterator = contacts.iterator();
        while ((contact = contactsIterator.next()) != null) {
            Iterator<GroupData> groupsIterator = groups.iterator();
            while((group = groupsIterator.next()) != null) {
                if (contact.getGroups().contains(group)){
                    foundContactInGroup = true;
                    break;
                }
            }
            if(foundContactInGroup){
                break;
            }
        }

        group = group != null ? group : groups.iterator().next();
        if(!foundContactInGroup) {
            contact = createContact();
            app.goTo().contactPage();
            app.contact().addToGroup(contact, group);
        }
        app.goTo().contactPage();

        app.contact().removeFromGroup(contact, group);

        ContactData finalContact = contact;

        ContactData contactFromDb = app.db()
                .contacts().stream()
                .filter(c -> c.getId() == finalContact.getId())
                .findFirst().orElse(null);

        assertEquals(false, contactFromDb.getGroups().contains(group));
    }
}

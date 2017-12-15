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

public class AddContactToGroupTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            createContact();
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withGroupName("NewI")
                    .withGroupHeader("NewI Header").withGroupFooter("NewI Footer"));
        }
    }

    private void createContact() {
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
    }


    @Test
    public void testAddContactToGroup(){
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData contact;
        GroupData group = null;
        boolean foundContactNotInGroup = false;

        Iterator<ContactData> contactsIterator = contacts.iterator();
        while ((contact = contactsIterator.next()) != null) {
            Iterator<GroupData> groupsIterator = groups.iterator();
            while((group = groupsIterator.next()) != null) {
                if (!contact.getGroups().contains(group)){
                    foundContactNotInGroup = true;
                    break;
                }
            }
            if(foundContactNotInGroup){
                break;
            }
        }

        if(!foundContactNotInGroup) {
            createContact();
        }
        group = group != null ? group : groups.iterator().next();
        app.goTo().contactPage();
        app.contact().addToGroup(contact, group);
        ContactData finalContact = contact;
        ContactData contactFromDb = app.db().contacts().stream()
                .filter(c -> c.getId() == finalContact.getId())
                .findFirst().orElse(null);
        assertEquals(true, contactFromDb.getGroups().contains(group));
    }
}
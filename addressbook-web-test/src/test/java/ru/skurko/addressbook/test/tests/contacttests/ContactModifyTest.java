package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModifyTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().all().size() == 0) {
            app.contact().create((new ContactData()
                    .withFirstName("A")
                    .withMiddleName("A")
                    .withLastName("A")
                    .withNickName("A")
                    .withGroup("A")), true);
        }
    }

    @Test (enabled = true)
    public void testContactModify() {
        Set<ContactData> before =app.contact().all();
        ContactData modifyContact = before.iterator().next();

        ContactData contact = new ContactData().withId(modifyContact.getId())
                .withFirstName("Иван")
                .withMiddleName("Иванович")
                .withLastName("Иванов")
                .withNickName("III")
                .withGroup("NewI");
        app.contact().modify(contact);
        app.goTo().contactPage();
        Set<ContactData> after =app.contact().all();

        Assert.assertEquals(after.size(), before.size());
        before.remove(modifyContact);
        before.add(contact);

        Assert.assertEquals(before,after);
    }
}
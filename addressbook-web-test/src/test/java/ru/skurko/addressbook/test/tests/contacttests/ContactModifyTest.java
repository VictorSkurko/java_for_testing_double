package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModifyTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.db().contacts().size() == 0) {
            app.contact().create((new ContactData()
                    .withFirstName("A")
                    .withMiddleName("A")
                    .withLastName("A")
                    .withNickName("A")
                    .withGroup("NewI")), true);
        }
    }

    @Test (enabled = true)
    public void testContactModify() {
        Contacts before =app.db().contacts();
        ContactData modifyContact = before.iterator().next();

        ContactData contact = new ContactData().withId(modifyContact.getId())
                .withFirstName("Иван")
                .withMiddleName("Иванович")
                .withLastName("Иванов")
                .withNickName("III")
                .withGroup("NewI");
        app.contact().modify(contact);
        app.goTo().contactPage();

        //Хэширование. Предпроверка размеров списков
        assertThat(app.contact().count(), equalTo(before.size()));

        Contacts after =app.db().contacts();
        Assert.assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.withOut(modifyContact).withAdded(contact)));
    }
}
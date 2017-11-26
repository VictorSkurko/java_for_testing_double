package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;

public class ContactDeletedTest extends TestBase {

    @Test
    public void testContactDeleted() {

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }

        app.getContactHelper().selectContact();

        app.getContactHelper().deleteContact();

        app.alertOk();

        app.getSessionHelper().logout();
    }
}
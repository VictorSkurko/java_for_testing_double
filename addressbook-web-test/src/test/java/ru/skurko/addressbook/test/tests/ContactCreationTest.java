package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        app.getContactHelper().initContactForm();

        app.getContactHelper().fillContactForm(new ContactData(
                "Владимир",
                "Александрович",
                "Александров",
                "VAAl",
                "NewI"), true);

        app.getContactHelper().submitContactForm();

        app.getSessionHelper().logout();
    }
}
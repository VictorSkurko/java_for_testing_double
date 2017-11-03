package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;

public class ContactDeletedTest extends TestBase {

    @Test
    public void testContactDeleted() {

        app.getContactHelper().selectContact();

        app.getContactHelper().deleteContact();

        app.alertOk();

        app.getSessionHelper().logout();
    }
}
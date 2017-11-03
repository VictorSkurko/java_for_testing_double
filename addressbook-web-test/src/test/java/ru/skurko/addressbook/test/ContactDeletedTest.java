package ru.skurko.addressbook.test;

import org.testng.annotations.Test;

public class ContactDeletedTest extends TestBase{

    @Test
    public void testContactDeleted() {

        selectContact();

        deleteContact();

        alertOk();

        logout();
    }
}

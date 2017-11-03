package ru.skurko.addressbook.test;

import org.testng.annotations.Test;

public class ContactDeletedTest extends TestBase{

    @Test
    public void testContactDeleted() {

        for (int i = 0; i < 5; i++) {
        selectContact();

        deleteContact();

        alertOk();
        }
        logout();
    }
}
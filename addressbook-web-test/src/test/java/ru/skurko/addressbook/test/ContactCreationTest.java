package ru.skurko.addressbook.test;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase{

    @Test
    public void testContactCreation() {

        initContactForm();

        fillContactForm("Василий", "Васильевич", "Васильев", "Vas");

        submitContactForm();

        logout();
    }
}
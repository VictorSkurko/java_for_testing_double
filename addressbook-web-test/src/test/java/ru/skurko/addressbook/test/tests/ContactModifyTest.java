package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;

public class ContactModifyTest extends TestBase {

    @Test
    public void testContactModify(){
        app.getContactHelper().modifyContact();
        app.getContactHelper().fillContactForm(new ContactData(
                "Александр XXX",
                "Александрович XXX",
                "Александров XXX",
                "Al XXX"));
        app.getContactHelper().submitModifyContact();
    }
}
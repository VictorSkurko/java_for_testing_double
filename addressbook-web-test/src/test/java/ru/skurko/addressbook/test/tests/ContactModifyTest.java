package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;

public class ContactModifyTest extends TestBase {

    @Test
    public void testContactModify(){

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }
        app.getContactHelper().modifyContact();
        app.getContactHelper().fillContactForm(new ContactData(
                "Александр",
                "Александрович",
                "Александров",
                "Al",
                "NewI"),false);
        app.getContactHelper().submitModifyContact();
//Выход
        app.getSessionHelper().logout();
    }
}
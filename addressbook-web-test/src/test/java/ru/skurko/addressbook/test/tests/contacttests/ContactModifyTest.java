package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactModifyTest extends TestBase {


    @Test (enabled = true)
    public void testContactModify() {

        app.goTo().goToContactPage();
        List<ContactData> before =app.getContactHelper().getContactList();
        int index = before.size() -1;

        app.getContactHelper().modifyContact(index);

        //Модифицируем контакт
        ContactData contact = new ContactData(
                before.get(index).getId(),
                "Alexander-140",
                "Al.",
                "Alexandrov",
                "Al",
                "NewI");

        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitModifyContact();
        app.goTo().goToContactPage();

        List<ContactData> after =app.getContactHelper().getContactList();

        //Сравниваем количество контактов до и после модификации
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println("Before modify contact:\n" + before);
        System.out.println("After modify contact:\n" + after);

        Assert.assertEquals(before,after);

//Выход
//        app.getSessionHelper().logout();
    }
}
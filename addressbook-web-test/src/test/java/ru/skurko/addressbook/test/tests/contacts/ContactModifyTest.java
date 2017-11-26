package ru.skurko.addressbook.test.tests.contacts;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.HashSet;
import java.util.List;

public class ContactModifyTest extends TestBase {

    @Test
    public void testContactModify() {

        //Переходим на страницу групп и проверяем наличие
        app.getNavigationHelper().goToGroupPage();

        //Если нет группы, то создаем
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData(

                    "NewI",
                    null,
                    null));

        }
        app.getNavigationHelper().goToContactPage();

        //Если контакта нет, то создаем контакт для модификации
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }

        app.getNavigationHelper().goToContactPage();
        //Считаем количество контактов
        List<ContactData> before = app.getContactHelper().getContactList();

        //Модифицируем контакт
//        app.getContactHelper().modifyContact();
        app.getContactHelper().modifyContact(before.size()-1);

        ContactData contact = new ContactData(before.get(before.size()-1).getId(),
                "Vladimir",
                "Александрович",
                "Ivanov",
                "Al",
                "NewI");

        app.getContactHelper().fillContactForm((contact), false);
        app.getContactHelper().submitModifyContact();
        app.getNavigationHelper().goToContactPage();

        //Считаем колоичество контактов после модификации

        List<ContactData> after = app.getContactHelper().getContactList();

        //Сравниваем количество контактов до и после модификации
        Assert.assertEquals(after.size(), before.size());


        before.remove(before.size()-1);
        before.add(contact);

        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

//Выход
        app.getSessionHelper().logout();
    }
}
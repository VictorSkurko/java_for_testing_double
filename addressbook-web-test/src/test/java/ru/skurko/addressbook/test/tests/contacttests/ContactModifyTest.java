package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

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

        List<ContactData> before =app.getContactHelper().getContactList();
//        int before = app.getContactHelper().getContactCount();

        //Модифицируем контакт
        app.getContactHelper().modifyContact(before.size()-1);
        app.getContactHelper().fillContactForm(new ContactData(
                "Alexander-4",
                "Al.",
                "Alexandrov",
                "Al",
                "NewI"), false);
        app.getContactHelper().submitModifyContact();
        app.getNavigationHelper().goToContactPage();

        List<ContactData> after =app.getContactHelper().getContactList();
        //Проверим количество контактов после модификации
//        int after = app.getContactHelper().getContactCount();

        //Сравниваем количество контактов до и после модификации
        Assert.assertEquals(after.size(), before.size());

//Выход
        app.getSessionHelper().logout();
    }
}
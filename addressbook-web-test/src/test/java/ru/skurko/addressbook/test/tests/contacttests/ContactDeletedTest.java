package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.List;

public class ContactDeletedTest extends TestBase {

    @Test
    public void testContactDeleted() {

        //Переходим на страницу групп и проверяем наличие групп
        app.getNavigationHelper().goToGroupPage();

        //Если нет группы, то создаем
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData(

                    "NewI",
                    null,
                    null));
        }

        //Идем на страницу контактов
        app.getNavigationHelper().goToContactPage();

        //Проверим количество контактов до удаления
        List<ContactData> before =app.getContactHelper().getContactList();
//        int before = app.getContactHelper().getContactCount();

        //Если контактов нет, то создаем контакт для удаления
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }

        //Выбираем элемент для удаления по индексу
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteContact();

        //Обрабатываем алерт - подтверждаем удаление контакта
        app.alertOk();

        app.getNavigationHelper().goToContactPage();

        //Считаем количество контактов после удаления
        List<ContactData> after =app.getContactHelper().getContactList();
//        int after = app.getContactHelper().getContactCount();

        Assert.assertEquals(after.size(), before.size()-1);

        app.getSessionHelper().logout();
    }
}
package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

public class ContactDeletedTest extends TestBase {

    @Test
    public void testContactDeleted() {

        //Переходим на страницу групп и проверяем наличие
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
        int before = app.getContactHelper().getContactCount();

        //Если контактов нет, то создаем контакт для удаления
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();

        //Обрабатываем алерт - подтверждаем удаление контакта
        app.alertOk();

        app.getNavigationHelper().goToContactPage();

        //Считаем количество контактов после удаления
        int after = app.getContactHelper().getContactCount();

        Assert.assertEquals(after, before-1);

        app.getSessionHelper().logout();
    }
}
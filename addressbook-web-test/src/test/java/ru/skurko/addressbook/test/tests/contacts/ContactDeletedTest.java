package ru.skurko.addressbook.test.tests.contacts;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.List;

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

        //Если контактов нет, то создаем контакт для удаления
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "A",
                    "А",
                    "A",
                    "AAA",
                    "NewI"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size()-1);

        app.getContactHelper().deleteContact();

        //Обрабатываем алерт - подтверждаем удаление контакта
        app.alertOk();

        app.getNavigationHelper().goToContactPage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size()-1);

        //Выравниваем размеры списков
        before.remove(before.size()-1);

        //Проверяем совпадение элементов
        Assert.assertEquals(before, after);

        app.getSessionHelper().logout();
    }
}
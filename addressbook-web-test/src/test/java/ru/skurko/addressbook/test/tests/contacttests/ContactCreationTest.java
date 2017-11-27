package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        //Переходим на страницу групп и проверяем наличие
        app.getNavigationHelper().goToGroupPage();

        //Если нет группы, то создаем
        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "NewI",
                    null,
                    null));
        }

        app.getNavigationHelper().goToContactPage();
        //Проверим количество контактов до добавления
        int before = app.getContactHelper().getContactCount();

        app.getContactHelper().createContact(new ContactData(
                "Владимир",
                "Александрович",
                "Александров",
                "VAAl",
                "NewI"), true);

        app.getNavigationHelper().goToContactPage();
        int after = app.getContactHelper().getContactCount();

        Assert.assertEquals(after, before+1);

        app.getSessionHelper().logout();
    }
}
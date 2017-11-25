package ru.skurko.addressbook.test.tests.contacts;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.List;

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
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().createContact(new ContactData(
                "Владимир",
                "Александрович",
                "Александров",
                "VAAl",
                "NewI"), true);

        app.getNavigationHelper().goToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size()+1);

        app.getSessionHelper().logout();
    }
}
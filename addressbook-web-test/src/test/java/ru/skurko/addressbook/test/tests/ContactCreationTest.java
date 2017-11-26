package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;

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

        app.getContactHelper().createContact(new ContactData(
                "Владимир",
                "Александрович",
                "Александров",
                "VAAl",
                "NewI"), true);

        app.getSessionHelper().logout();
    }
}
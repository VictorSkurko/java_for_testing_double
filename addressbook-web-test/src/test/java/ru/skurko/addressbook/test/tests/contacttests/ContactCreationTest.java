package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test (enabled = true)
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

        List<ContactData> before =app.getContactHelper().getContactList();

        ContactData contact =  new ContactData(
                "Владимир-2",
                "Александрович",
                "Александров-2",
                "VAAl",
                "NewI");

        app.getContactHelper().createContact(contact, true);

        app.getNavigationHelper().goToContactPage();

        List<ContactData> after =app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size()+1);

        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println("Before create contact:\n" + before);
        System.out.println("After create contact:\n" + after);

        Assert.assertEquals(before,after);
    }
}
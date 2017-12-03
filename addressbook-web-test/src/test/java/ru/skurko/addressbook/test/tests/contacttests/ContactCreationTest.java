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
        app.goTo().groupPage();

        //Если нет группы, то создаем
        if (!app.group().isThereAGroup()){
            app.group().create(new GroupData(

                    "NewI",
                    null,
                    null));
        }

        app.goTo().goToContactPage();

        List<ContactData> before =app.getContactHelper().getContactList();

        ContactData contact =  new ContactData(
                "Владимир-2",
                "Александрович",
                "Александров-2",
                "VAAl",
                "NewI");

        app.getContactHelper().createContact(contact, true);

        app.goTo().goToContactPage();

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
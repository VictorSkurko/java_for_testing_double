package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        //Переходим на страницу групп и проверяем наличие
        app.goTo().groupPage();

        //Если нет группы, то создаем
        if (!app.group().isThereAGroup()) {
            app.group().create(new GroupData().withGroupName("NewI"));
        }
        app.goTo().contactPage();
    }

    @Test (enabled = true)
    public void testContactCreation() {
        List<ContactData> before =app.contact().list();
        ContactData contact =  new ContactData()
                .withFirstName("Василий")
                .withMiddleName("Иванович")
                .withLastName("Чапаев")
                .withNickName("VI")
                .withGroup("NewI");
        app.contact().create(contact, true);
        app.goTo().contactPage();
        List<ContactData> after =app.contact().list();
        Assert.assertEquals(after.size(), before.size()+1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }
}
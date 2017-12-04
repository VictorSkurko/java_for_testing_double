package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Set;

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
        Set<ContactData> before =app.contact().all();
        ContactData contact =  new ContactData()
                .withFirstName("Василий")
                .withMiddleName("Иванович")
                .withLastName("Чапаев")
                .withNickName("VI")
                .withGroup("NewI");
        app.contact().create(contact, true);
        app.goTo().contactPage();
        Set<ContactData> after =app.contact().all();
        Assert.assertEquals(after.size(), before.size()+1);

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before,after);
    }
}
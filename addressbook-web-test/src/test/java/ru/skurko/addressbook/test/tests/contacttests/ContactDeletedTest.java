package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactDeletedTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        //Переходим на страницу групп и проверяем наличие групп
        app.goTo().groupPage();

        //Если нет группы, то создаем
        if (!app.group().isThereAGroup()) {
            app.group().create(new GroupData().withGroupName("NewI"));
        }

        //Идем на страницу контактов
        app.goTo().contactPage();

        //Если контактов нет, то создаем контакт для удаления
        if (app.contact().list().size() ==0) {
            app.contact().create((new ContactData()
                            .withFirstName("A")
                            .withMiddleName("A")
                            .withLastName("A")
                            .withNickName("A")
                            .withGroup("A")), true);
        }
    }

    @Test (enabled = true)
    public void testContactDeleted() {
        List<ContactData> before =app.contact().list();
        int index = before.size()-1;
        app.contact().delete(index);
        app.alertOk();
        app.goTo().contactPage();
        List<ContactData> after =app.contact().list();
        Assert.assertEquals(after.size(), before.size()-1);
        before.remove(index);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }
}
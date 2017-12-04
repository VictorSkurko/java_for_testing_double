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
        app.goTo().goToContactPage();

        //Если контактов нет, то создаем контакт для удаления
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact((new ContactData()
                            .withFirstName("A")
                            .withMiddleName("A")
                            .withLastName("A")
                            .withNickName("A")
                            .withGroup("A")), true);
        }
    }

    @Test (enabled = true)
    public void testContactDeleted() {
        //Проверим количество контактов до удаления
        List<ContactData> before =app.getContactHelper().getContactList();
        int index = before.size()-1;
        //Выбираем элемент для удаления по индексу
        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteContact();
        app.alertOk();
        app.goTo().goToContactPage();
        List<ContactData> after =app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), index);
        before.remove(index);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }
}
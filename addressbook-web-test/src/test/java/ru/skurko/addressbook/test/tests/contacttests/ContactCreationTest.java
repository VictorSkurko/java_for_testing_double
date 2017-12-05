package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Contacts before =app.contact().all();
        ContactData contact =  new ContactData()
                .withFirstName("Василий")
                .withMiddleName("Иванович")
                .withLastName("Чапаев")
                .withNickName("VI")
                .withGroup("NewI")
                .withHomePhone("+8 8635 25 00 00")
                .withMobilePhone("+7 900 777 7777")
                .withWorkPhone("+8 800 666 6666");

        app.contact().create(contact, true);
        app.goTo().contactPage();

        //Хэширование. Предпроверка размеров списков
        assertThat(app.contact().count(), equalTo(before.size()+1));

        Contacts after =app.contact().all();

        assertThat(after, equalTo(before.withAdded(
                contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}
package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;


import java.io.File;

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
        File photo = new File("src/test/resources/images.jpg");
        ContactData contact =  new ContactData()
                .withFirstName("Василий")
                .withMiddleName("Иванович")
                .withLastName("Чапаев")
                .withNickName("VI")
                .withAddress("Россия, Ростов-на-Дону")
                .withPhoto(photo)
                .withGroup("NewI")
                .withHomePhone("+8 8635 25 00 00")
                .withMobilePhone("+7 900 777 7777")
                .withWorkPhone("+8 811 666 6666")
                .withEmail("work@mail.ru")
                .withEmail2("all@mail.com")
                .withEmail3("other@mail.ru");

        app.contact().create(contact, true);
        app.goTo().contactPage();

        //Хэширование. Предпроверка размеров списков
        assertThat(app.contact().count(), equalTo(before.size()+1));

        Contacts after =app.contact().all();

        assertThat(after, equalTo(before.withAdded(
                contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/images.jpg");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}
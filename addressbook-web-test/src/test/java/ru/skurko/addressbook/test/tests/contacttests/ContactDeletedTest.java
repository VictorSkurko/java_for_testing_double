package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
public class ContactDeletedTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        //Переходим на страницу групп и проверяем наличие групп
        app.goTo().groupPage();

//        //Если нет группы, то создаем
//        if (!app.group().isThereAGroup()) {
//            app.group().create(new GroupData().withGroupName("NewI"));
//        }
        //проверяем наличие группы
        if (app.db().groups().size() == 0){
            app.group().create(new GroupData().withGroupName("NewI"));
        }

        //Идем на страницу контактов
        app.goTo().contactPage();

        //Если контактов нет, то создаем контакт для удаления
        if (app.contact().all().size() ==0) {
            app.contact().create((new ContactData()
                            .withFirstName("A")
                            .withMiddleName("A")
                            .withLastName("A")
                            .withNickName("A")),
//                            .withGroup("NewI")),
                    true);
        }
    }

    @Test (enabled = true)
    public void testContactDeleted() {
        Contacts before =app.db().contacts();

        ContactData deletedContact = before.iterator().next();

        app.contact().delete(deletedContact);
        app.alertOk();
        app.goTo().contactPage();

        //Хэширование. Предпроверка размеров списков
        assertThat(app.contact().count(), equalTo(before.size()-1));

        Contacts after =app.db().contacts();
        Assert.assertEquals(after.size(), before.size()-1);

        assertThat(after, equalTo(before.withOut(deletedContact)));
    }
}
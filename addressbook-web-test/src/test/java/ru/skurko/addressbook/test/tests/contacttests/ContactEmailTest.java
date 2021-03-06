package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

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
        if (app.contact().all().size() ==0) {
            app.contact().create((new ContactData()
                    .withFirstName("Василий")
                    .withMiddleName("Иванович")
                    .withLastName("Чапаев")
                    .withNickName("VI")
                    .withAddress("Россия, Ростов-на-Дону")
//                    .withGroup("NewI")
                    .withHomePhone("+8 8635 25 00 00")
                    .withMobilePhone("+7 900 777 7777")
                    .withWorkPhone("+8 811 666 6666")
                    .withEmail("work@mail.ru")
                    .withEmail2("all@mail.com")
                    .withEmail3("other@mail.ru")), true);
        }
    }

    @Test
    public void testContactEmail() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmail(), equalTo(mergeEmail(contactInfoFromEditForm)));
    }

    private String mergeEmail(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((m) -> !m.equals(""))
                .map(ContactEmailTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email) {
        return email.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
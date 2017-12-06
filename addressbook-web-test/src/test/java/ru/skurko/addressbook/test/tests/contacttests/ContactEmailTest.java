package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

    @Test
    public void testContactEmail() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmail(), equalTo(mergeEmail(contactInfoFromEditForm)));
    }

    //Формируем коллекцию из адресов почты
    private String mergeEmail(ContactData contact) {
        //Создаем список из адресов почты
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((m) -> !m.equals(""))
                .map(ContactEmailTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email) {
        return email.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
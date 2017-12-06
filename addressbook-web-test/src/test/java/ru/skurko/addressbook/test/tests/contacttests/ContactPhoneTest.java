package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTest extends TestBase {

    @Test
    public void testContactPhone() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

        //Формируем коллекцию из телефонов
    private String mergePhones(ContactData contact){
        //Создаем список из номеров
        return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(), contact.getWorkPhone())
                //Здесь список переводим в поток и работаем с лямбдой
                //фильтруем из потока пустые строки.
                //т.е. например, нет мобильного телефона
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactPhoneTest::cleaned)
                //Затем при помощи Collectors клеим строки
                .collect(Collectors.joining("\n"));
        //...и возвращаем метод
    }


    //Пишем очистку телефонных номеров от ненужных символов
    //шаблон, заменяющий первый параметр на второй параметр

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }
}
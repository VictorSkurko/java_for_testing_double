package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTest extends TestBase {

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
                    .withGroup("NewI")
                    .withHomePhone("+8 8635 25 00 00")
                    .withMobilePhone("+7 900 777 7777")
                    .withWorkPhone("+8 811 666 6666")
                    .withEmail("work@mail.ru")
                    .withEmail2("all@mail.com")
                    .withEmail3("other@mail.ru")), true);
        }
    }

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
//Аналогично делаем класы для сравнения адресов и электронных
// почт на главной странице и странице редактирования
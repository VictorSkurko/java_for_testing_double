package ru.skurko.addressbook.test.tests.contacttests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.tests.TestBase;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        BufferedReader reader = new BufferedReader
                (new FileReader((new File("src/test/resources/contacts.xml"))));

        String xml = "";

        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>)xstream.fromXML(xml);
        return contacts.stream()
                .map((g)-> new Object[]{g})
                .collect(Collectors.toList())
                .iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        BufferedReader reader = new BufferedReader
                (new FileReader((new File("src/test/resources/contacts.json"))));

        String json = "";

        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }

        Gson gson = new Gson();
        List<ContactData> contacts = gson
                .fromJson(json, new TypeToken<List<ContactData>>(){}.getType()); //Нет слов
        return contacts.stream()
                .map((g) -> new Object[]{g})
                .collect(Collectors.toList())
                .iterator();
    }

    @BeforeMethod
    public void ensurePreconditions(){
        //Переходим на страницу групп и проверяем наличие
//        app.goTo().groupPage();

        //Если нет группы, то создаем
//        if (!app.group().isThereAGroup()) {
//            app.group().create(new GroupData().withGroupName("NewI"));
//        }
        app.goTo().groupPage();

        //проверяем наличие группы
        if (app.db().groups().size() == 0){
            app.group().create(new GroupData().withGroupName("NewI"));
        }
        app.goTo().contactPage();
    }

    @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        Contacts before =app.db().contacts();
//        File photo = new File("src/test/resources/images.jpg");
//        ContactData contact =  new ContactData()
//                .withFirstName("Василий")
//                .withMiddleName("Иванович")
//                .withLastName("Чапаев")
//                .withNickName("VI")
//                .withAddress("Россия, Ростов-на-Дону")
//                .withPhoto(photo)
//                .withGroup("NewI")
//                .withHomePhone("+8 8635 25 00 00")
//                .withMobilePhone("+7 900 777 7777")
//                .withWorkPhone("+8 811 666 6666")
//                .withEmail("work@mail.ru")
//                .withEmail2("all@mail.com")
//                .withEmail3("other@mail.ru");

        app.contact().create(contact, true);
        app.goTo().contactPage();

        //Хэширование. Предпроверка размеров списков
        assertThat(app.contact().count(), equalTo(before.size()+1));

        Contacts after =app.db().contacts();

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
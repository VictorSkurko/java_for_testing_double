package ru.skurko.addressbook.test.tests.grouptests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.tests.TestBase;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

    Logger logger = LoggerFactory.getLogger(GroupCreationTest.class);

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        BufferedReader reader = new BufferedReader
                (new FileReader((new File("src/test/resources/groups.xml"))));

        String xml = "";

        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>)xstream.fromXML(xml);
        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        BufferedReader reader = new BufferedReader
                (new FileReader((new File("src/test/resources/groups.json"))));

        String json = "";

        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }

        Gson gson = new Gson();
        List<GroupData> groups = gson
                .fromJson(json, new TypeToken<List<GroupData>>(){}.getType()); //Нет слов
        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validGroupsFromJson")

    public void testGroupCreation(GroupData group) {

        logger.info("Start testGroupCreation");

        app.goTo().groupPage();

        Groups before = app.db().groups();

        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));

        Groups after = app.db().groups();

        //Используем Hamcrest
        assertThat(after, equalTo(before.withAdded(
                group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

        verifyGroupListInUi();

        logger.info("Finish testGroupCreation");
    }

    @Test(enabled = false)
    //Негативный тест на проверку апострофа ("Test'") в названии группы
    public void testBadGroupCreation() {

        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withGroupName("Test'");
        app.group().create(group);

        //Хеширование. Предпроверка. если тест завершается не успешно
        //то тест падает гораздо быстрее.
        assertThat(app.group().count(), equalTo(before.size()));


        Groups after = app.group().all();
        //Используем Hamcrest
        assertThat(after, equalTo(before));
        verifyGroupListInUi();
    }
}
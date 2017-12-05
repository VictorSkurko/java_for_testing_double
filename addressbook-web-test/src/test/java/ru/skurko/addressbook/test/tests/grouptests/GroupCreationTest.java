package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        Groups before = app.group().all();

        GroupData group = new GroupData().withGroupName("TestIV");

        app.group().create(group);

        //Хеширование. Предпроверка. если тест завершается не успешно
        //то тест падает гораздо быстрее.
        assertThat(app.group().count(), equalTo(before.size()+1));

        Groups after = app.group().all();

        //Используем Hamcrest
        assertThat(after, equalTo(before.withAdded(
                group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    }

    @Test
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
    }
}
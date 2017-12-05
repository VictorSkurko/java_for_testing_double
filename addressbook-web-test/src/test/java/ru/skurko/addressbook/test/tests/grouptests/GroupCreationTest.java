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

        Groups after = app.group().all();

        assertThat(after.size(), equalTo(before.size()+1));

        //Используем Hamcrest
        assertThat(after, equalTo(before.withAdded(
                group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    }
}
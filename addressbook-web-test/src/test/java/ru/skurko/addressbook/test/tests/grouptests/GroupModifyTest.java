package ru.skurko.addressbook.test.tests.grouptests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupModifyTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();

        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withGroupName("Modify Group"));
        }
    }

    @Test
    public void testGroupModify() {

        Groups before = app.group().all();
        GroupData modifyGroup = before.iterator().next();

        GroupData group = new GroupData()
                .withId(modifyGroup.getId())
                .withGroupName("NewII")
                .withGroupHeader("HomeHeader after Modify")
                .withGroupFooter("HomeFooter after Modify");

        app.group().modify(group);
        app.goTo().groupPage();

        //Хеширование. Предпроверка. если тест завершается не успешно
        //то тест падает гораздо быстрее.
        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.group().all();
        assertThat(after, equalTo(before.withOut(modifyGroup).withAdded(group)));
    }
}
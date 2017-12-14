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
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withGroupName("Modify Group"));
        }
    }

    @Test
    public void testGroupModify() {

            app.goTo().groupPage();
        Groups before = app.db().groups();
        GroupData modifyGroup = before.iterator().next();

        GroupData group = new GroupData()
                .withId(modifyGroup.getId())
                .withGroupName("NewI")
                .withGroupHeader("HomeHeader after Modify")
                .withGroupFooter("HomeFooter after Modify");

        app.group().modify(group);
        app.goTo().groupPage();

        //Хеширование. Предпроверка. если тест завершается не успешно
        //то тест падает гораздо быстрее. В принципе - уже не надо, т.к. проверка идет
        //из БД.
        //Но оставим для минимального контроля пользовательского интерфейса
        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.db().groups();
        assertThat(after, equalTo(before.withOut(modifyGroup).withAdded(group)));

        verifyGroupListInUi();
    }
}
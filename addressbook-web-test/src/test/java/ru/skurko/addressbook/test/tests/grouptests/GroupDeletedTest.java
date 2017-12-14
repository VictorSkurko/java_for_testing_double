package ru.skurko.addressbook.test.tests.grouptests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import java.util.Set;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import ru.skurko.addressbook.test.tests.TestBase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletedTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();

        //если не существует групп для удаления, то создаем группу
        if (app.db().groups().size() == 0){
            app.group().create(new GroupData().withGroupName("Group for Delete"));
        }
    }

    @Test
    public void testGroupDeleted() {

        app.goTo().groupPage();
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);

        //Хеширование. Предпроверка. если тест завершается не успешно
        //то тест падает гораздо быстрее.
        assertThat(app.group().count(), equalTo(before.size()-1));

        Groups after = app.db().groups();
        assertThat(after, equalTo(before.withOut(deletedGroup)));
        verifyGroupListInUi();
    }
}
package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Set;

public class GroupDeletedTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();

        //если не существует групп для удаления, то создаем группу
        if (app.group().all().size() == 0){
            app.group().create(new GroupData().withGroupName("Group for Delete"));
        }
    }

    @Test
    public void testGroupDeleted() {

        app.goTo().groupPage();

        Set<GroupData> before = app.group().all();

        GroupData deletedGroup = before.iterator().next();

        app.group().delete(deletedGroup);

        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedGroup);

        Assert.assertEquals(before,after);
    }
}
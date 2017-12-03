package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Set;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        Set<GroupData> before = app.group().all();

        GroupData group = new GroupData().withGroupName("TestIII");

        app.group().create(group);

        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size()+1);

        group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
        before.add(group);

        Assert.assertEquals(before, after);
    }
}
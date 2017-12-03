package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        List<GroupData> before = app.group().list();

        GroupData group = new GroupData().withGroupName("TestIII");

        app.group().create(group);

        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size()+1);

        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);
        System.out.println(before);
        System.out.println(after);

//        app.getSessionHelper().logout();
    }
}
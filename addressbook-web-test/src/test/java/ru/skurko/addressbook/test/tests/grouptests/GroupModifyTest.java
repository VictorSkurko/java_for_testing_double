package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class GroupModifyTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.goTo().groupPage();

        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withGroupName("Modify Group"));
        }
    }

    @Test
    public void testGroupModify() {

        List<GroupData> before = app.group().list();
        int index = before.size() - 1;

        GroupData group = new GroupData()
                .withId(before.get(index).getId())
                .withGroupName("NewII")
                .withGroupHeader("HomeHeader after Modify")
                .withGroupFooter("HomeFooter after Modify");

        app.group().modify(index, group);
        app.goTo().groupPage();

        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(before, after);
    }
}
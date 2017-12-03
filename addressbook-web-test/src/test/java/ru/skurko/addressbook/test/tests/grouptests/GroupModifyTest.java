package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Set;

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

        Set<GroupData> before = app.group().all();
        GroupData modifyGroup = before.iterator().next();

        GroupData group = new GroupData()
                .withId(modifyGroup.getId())
                .withGroupName("NewII")
                .withGroupHeader("HomeHeader after Modify")
                .withGroupFooter("HomeFooter after Modify");

        app.group().modify(group);
        app.goTo().groupPage();

        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size());

        before.remove(modifyGroup);
        before.add(group);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(before, after);
    }
}
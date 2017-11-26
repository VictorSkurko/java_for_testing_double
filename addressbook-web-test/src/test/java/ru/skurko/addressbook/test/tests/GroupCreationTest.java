package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();

//        int before = app.getGroupHelper().getGroupCount();


        GroupData group = new GroupData(

                "NewI",
                null,
                null);

        app.getGroupHelper().createGroup(group);

        List<GroupData> after = app.getGroupHelper().getGroupList();
//        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after.size(), before.size()+1);

        int max = 0;
        for (GroupData g : after) {
            if (g.getId() > max) {
                max = g.getId();
            }
        }
        group.setId(max);

        before.add(group);

        Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));

        app.getSessionHelper().logout();
    }
}
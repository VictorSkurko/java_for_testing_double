package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();

//        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().createGroup(new GroupData(

                "NewI",
                null,
                null));

        List<GroupData> after = app.getGroupHelper().getGroupList();
//        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after.size(), before.size()+1);

        app.getSessionHelper().logout();
    }
}
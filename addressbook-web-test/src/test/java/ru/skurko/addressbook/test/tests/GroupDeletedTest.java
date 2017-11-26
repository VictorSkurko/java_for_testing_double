package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

import java.util.List;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        app.getNavigationHelper().goToGroupPage();

        //если не существует групп для удаления, то создаем группу
        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "Group for Delete",
                    null,
                    null));
        }

        List<GroupData> before = app.getGroupHelper().getGroupList();
//        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup(before.size()-1);
        app.getGroupHelper().deleteGroup();
        app.getGroupHelper().backToGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();
        //        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after.size(), before.size()-1);

        app.getSessionHelper().logout();
    }
}
package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        app.getNavigationHelper().goToGroupPage();


        //если не существует групп для удаления, то создаем группу
//        int before = app.getGroupHelper().getGroupCount();

        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "Group for Delete",
                    null,
                    null));
        }

        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deleteGroup();
        app.getGroupHelper().backToGroupPage();

        int after = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(after, before-1);

        app.getSessionHelper().logout();
    }
}
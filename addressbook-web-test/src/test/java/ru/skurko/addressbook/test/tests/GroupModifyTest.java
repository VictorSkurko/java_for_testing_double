package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupModifyTest extends TestBase{

    @Test
    public void testGroupModify(){
        app.getNavigationHelper().goToGroupPage();


        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "NewI",
                    "Modify Group",
                    null));
        }
        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup(before-1);
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().fillGroupForm(new GroupData(
                "NewI",
                "HomeHeader after Modify",
                "HomeFooter after Modify"));
        app.getGroupHelper().submitGroupModify();
        app.getNavigationHelper().goToGroupPage();

        int after = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(after, before);
    }
}
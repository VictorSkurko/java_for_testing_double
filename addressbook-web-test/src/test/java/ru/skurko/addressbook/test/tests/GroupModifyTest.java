package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupModifyTest extends TestBase{

    @Test
    public void testGroupModify(){
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().fillGroupForm(new GroupData(
                "New group Modify II",
                "HomeHeader Modify II",
                "HomeFooter"));
        app.getGroupHelper().submitGroupModify();
        app.getNavigationHelper().goToGroupPage();
    }
}
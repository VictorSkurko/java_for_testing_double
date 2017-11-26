package ru.skurko.addressbook.test.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();

        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().createGroup(new GroupData(

                "NewI",
                null,
                null));

        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after, before+1);

        app.getSessionHelper().logout();
    }
}
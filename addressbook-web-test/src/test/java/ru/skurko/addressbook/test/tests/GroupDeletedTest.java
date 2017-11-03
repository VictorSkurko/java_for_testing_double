package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        app.getNavigationHelper().goToGroupPage();

        app.getGroupHelper().selectGroup();

        app.getGroupHelper().deleteGroup();

        app.getGroupHelper().backToGroupPage();

        app.getSessionHelper().logout();
    }
}
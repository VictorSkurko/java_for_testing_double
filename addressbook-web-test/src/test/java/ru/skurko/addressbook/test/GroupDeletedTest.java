package ru.skurko.addressbook.test;

import org.testng.annotations.Test;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        goToGroupPage();

        selectGroup();

        deleteGroup();

        backToGroupPage();

        logout();
    }
}
package ru.skurko.addressbook.test;

import org.testng.annotations.Test;

public class GroupCreationTest extends TestBase{

    @Test
    public void testGroupCreation() {

        goToGroupPage();

        initGroupCreation();

        fillGroupForm(new GroupData("Home group", "HomeHeader", "HomeFooter"));

        backToGroupPage();

        logout();
    }
}
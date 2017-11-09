package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();

        app.getGroupHelper().initGroupCreation();

        app.getGroupHelper().fillGroupForm(new GroupData(

                "New group IV",
                null,
                null));

        app.getGroupHelper().submitGroupCreation();

        app.getGroupHelper().backToGroupPage();

        app.getSessionHelper().logout();
    }
}
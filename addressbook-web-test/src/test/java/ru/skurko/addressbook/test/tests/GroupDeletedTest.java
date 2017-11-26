package ru.skurko.addressbook.test.tests;

import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        app.getNavigationHelper().goToGroupPage();

        //если не существует групп для удаления, то создаем группу

        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "New group IV",
                    null,
                    null));
        }

        app.getGroupHelper().selectGroup();

        app.getGroupHelper().deleteGroup();

        app.getGroupHelper().backToGroupPage();

        app.getSessionHelper().logout();
    }
}
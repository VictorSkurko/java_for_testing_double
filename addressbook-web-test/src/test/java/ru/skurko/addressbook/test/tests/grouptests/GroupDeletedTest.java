package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.List;

public class GroupDeletedTest extends TestBase {

    @Test
    public void testGroupDeleted() {

        app.getNavigationHelper().goToGroupPage();

        //если не существует групп для удаления, то создаем группу
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData(

                    "Group for Delete",
                    null,
                    null));
        }

        List<GroupData> before = app.getGroupHelper().getGroupList();

        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().deleteGroup();
        app.getGroupHelper().backToGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size() - 1);

        //здесь before уже ссылается на старый список. в котором удален ненужный элемент
        // после этого действия старый список содержит те же элементы что и новый
        before.remove(before.size() - 1);

        // Теперь сравниваем списки поэлементно в цикле

            Assert.assertEquals(before, after);


        app.getSessionHelper().logout();
    }
}
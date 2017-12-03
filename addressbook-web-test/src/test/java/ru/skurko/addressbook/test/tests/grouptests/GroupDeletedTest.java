package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class GroupDeletedTest extends TestBase {

    @BeforeMethod

    public void ensurePreconditions() {
        app.getNavigationHelper().goToGroupPage();

        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "NewII",
                    "Modify Group",
                    null));
        }
    }

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

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(before,after);

//        app.getSessionHelper().logout();
    }
}
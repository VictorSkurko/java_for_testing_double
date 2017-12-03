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
        app.goTo().groupPage();

        //если не существует групп для удаления, то создаем группу
        if (app.group().list().size() == 0){
            app.group().create(new GroupData(

                    "Group for Delete",
                    null,
                    null));
        }
    }

    @Test
    public void testGroupDeleted() {

        app.goTo().groupPage();

        List<GroupData> before = app.group().list();
        int index = before.size() - 1;

        app.group().delete(index);

        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size() - 1);

        //здесь before уже ссылается на старый список. в котором удален ненужный элемент
        // после этого действия старый список содержит те же элементы что и новый
        before.remove(index);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(before,after);
    }
}
package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModifyTest extends TestBase {

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
    public void testGroupModify(){


        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size()-1;


        GroupData group = new GroupData(before.get(index).getId(),
                "NewII",
                "HomeHeader after Modify",
                "HomeFooter after Modify");

        app.getGroupHelper().modifyGroup(index, group);
        app.getNavigationHelper().goToGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(before,after);
    }
}
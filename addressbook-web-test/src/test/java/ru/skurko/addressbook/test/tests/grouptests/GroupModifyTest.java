package ru.skurko.addressbook.test.tests.grouptests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.tests.TestBase;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModifyTest extends TestBase {

    @Test
    public void testGroupModify(){
        app.getNavigationHelper().goToGroupPage();


        if (!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData(

                    "NewI",
                    "Modify Group",
                    null));
        }

        List<GroupData> before = app.getGroupHelper().getGroupList();

        app.getGroupHelper().selectGroup(before.size()-1);
        app.getGroupHelper().initGroupModify();

        GroupData group = new GroupData(before.get(before.size()-1).getId(),
                "NewI",
                "HomeHeader after Modify",
                "HomeFooter after Modify");

        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModify();
        app.getNavigationHelper().goToGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before,after);
    }
}
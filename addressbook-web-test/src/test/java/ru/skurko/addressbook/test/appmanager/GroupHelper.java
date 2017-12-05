package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void backToGroupPage() {
        click(By.linkText("group page"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getGroupName());
        type(By.name("group_header"), groupData.getGroupHeader());
        type(By.name("group_footer"), groupData.getGroupFooter());
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteGroup() {
        click(By.xpath("//div[@id='content']/form/input[5]"));
    }

    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
    }

    public void initGroupModify() {
        click(By.name("edit"));
    }

    public void submitGroupModify() {
        click(By.name("update"));
    }

    public void create(GroupData group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        //Когда группа меняется - сбрасываем кэш
        groupCache = null;
        backToGroupPage();
    }

    public void modify(GroupData group) {
        selectGroupById(group.getId());
        initGroupModify();
        fillGroupForm(group);
        submitGroupModify();
        //Когда группа меняется - сбрасываем кэш
        groupCache = null;
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        deleteGroup();
        //Когда группа меняется - сбрасываем кэш
        groupCache = null;
        backToGroupPage();
    }

    //Проверяем наличие элемента - т.е. есть ли хоть одна группа для удаления
    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    //Переменная для хранения кэша. Для оптимизации скорости тестов
    //Иначе нам придется создавать кэш по три раза для одного теста
    //Для одного теста это не очень заметно,
    //но если тестов много, то обращение к копии кэша сделает их
    //значительно быстрее.
    private Groups groupCache = null;

    public Groups all() {
        //Если кэш не пустой, то возвращаем его копию.
        if (groupCache != null) {
            return new Groups(groupCache);
        }
        //Иначе создаем кэш
        groupCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            groupCache.add(new GroupData().withId(id).withGroupName(name));
        }
        // и возвращаем копию кэша
        return new Groups(groupCache);
    }

}
package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.skurko.addressbook.test.model.ContactData;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactForm() {
        click(By.name("submit"));
    }

    public void initContactForm() {
        click(By.linkText("add new"));
    }

    public void deleteContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

//    public void selectContact(int index) {
//        wd.findElements(By.name("selected[]")).get(index).click();
//    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();

    }

    public void initContactEditbyId(int id) {
         wd.findElement(By.xpath("//a[contains(@href,'edit.php?id=" + id + "')]")).click();
     }


//    public void initContact(int index) {
//        wd.findElements(By.cssSelector("img[alt=\"Edit\"]")).get(index).click();
//    }

    public void modify(ContactData contact) {
        initContactEditbyId(contact.getId());
        fillContactForm(contact, false);
        submitModifyContact();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        // если это форма создания то creation будет true и выбираем:
        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitModifyContact() {
        click(By.name("update"));
    }

    public void create(ContactData contact, boolean b) {
        initContactForm();
        fillContactForm((contact), true);
        submitContactForm();
    }

    public void delete(ContactData group) {
        selectContactById(group.getId());
        deleteContact();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("entry")).size();
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();

            ContactData contact = new ContactData()
                    .withId(id)
                    .withFirstName(firstname)
                    .withMiddleName(null)
                    .withLastName(lastname)
                    .withNickName(null)
                    .withGroup("NewI");

            contacts.add(contact);
        }
        return contacts;
    }
}
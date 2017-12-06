package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;

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

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();

    }

    public void initContactEditbyId(int id) {
         wd.findElement(By.xpath("//a[contains(@href,'edit.php?id=" + id + "')]")).click();
     }

//     public void initContactEditbyId(int id) {
//         wd.findElement(By.cssSelector(String.format("a[href=edit.php?id='%s']", id))).click();
//     }


//    public void initContact(int index) {
//        wd.findElements(By.cssSelector("img[alt=\"Edit\"]")).get(index).click();
//    }


    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());



        // если это форма создания то creation будет true и выбираем:
        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void modify(ContactData contact) {
        initContactEditbyId(contact.getId());
        fillContactForm(contact, false);
        submitModifyContact();
        contactCache = null;
    }

    public void submitModifyContact() {
        click(By.name("update"));
    }

    public void create(ContactData contact, boolean b) {
        initContactForm();
        fillContactForm((contact), true);
        submitContactForm();
        contactCache = null;
    }

    public void delete(ContactData group) {
        selectContactById(group.getId());
        deleteContact();
        contactCache = null;

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("entry")).size();
    }

    private Contacts contactCache = null;


//    public Contacts all() {
//
//        if (contactCache != null) {
//            return new Contacts(contactCache);
//        }
//        contactCache = new Contacts();
//
//        List<WebElement> elements = wd.findElements(By.name("entry"));
//        for (WebElement element : elements) {
//            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
//            String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
//            String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
//
//
//            ContactData contact = new ContactData()
//                    .withId(id)
//                    .withFirstName(firstname)
//                    .withMiddleName(null)
//                    .withLastName(lastname)
//                    .withNickName(null)
//                    .withGroup("NewI");
//
//            contactCache.add(contact);
//        }
//        return contactCache;
//    }

    public Contacts all() {

        Contacts contacts = new Contacts();

        List<WebElement> rows = wd.findElements(By.name("entry"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String allPhones= cells.get(5).getText();

            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstname)
                    .withMiddleName(null)
                    .withLastName(lastname)
                    .withNickName(null)
                    .withGroup("NewI")
                    .withAllPhones(allPhones));
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactEditbyId(contact.getId());

        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");

        wd.navigate().back();
        return new ContactData()
                .withId(contact.getId())
                .withFirstName(firstname)
                .withLastName(lastname)
                .withHomePhone(home)
                .withMobilePhone(mobile)
                .withWorkPhone(work);
    }
}
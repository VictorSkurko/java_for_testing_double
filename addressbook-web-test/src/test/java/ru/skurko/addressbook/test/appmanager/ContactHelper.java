package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.GroupData;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
//        click(By.name("selected[]"));
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

    public void modifyContact(int index) {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
//        wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img")).get(index).click();
    }

    public void submitModifyContact() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact, boolean b) {
        initContactForm();
        fillContactForm((contact), true);
        submitContactForm();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
//        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {

            String id = element.findElement(By.tagName("input")).getAttribute("value");
            String name = element.getText();
            String lastname = element.getText();
            ContactData contact = new ContactData(id, name, null, lastname, null, "NewI");
            contacts.add(contact);
        }
        return contacts;
    }
}
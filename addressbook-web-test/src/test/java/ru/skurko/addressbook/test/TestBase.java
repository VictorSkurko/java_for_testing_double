package ru.skurko.addressbook.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class TestBase {
    FirefoxDriver wd;

    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        login("admin", "secret");
    }

    private void login(String username, String password) {
        wd.get("http://localhost/addressbook/");
        wd.findElement(By.name("user")).click();
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys(username);
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys(password);
        wd.findElement(By.xpath("//form[@id='LoginForm']/input[3]")).click();
    }

    protected void backToGroupPage() {
//        new WebDriverWait(wd, 10).until(ExpectedConditions.presenceOfElementLocated(By.linkText("group page"))).click();
        wd.findElement(By.linkText("group page")).click();
    }

    protected void logout() {
        wd.findElement(By.linkText("Logout")).click();
    }

    protected void fillGroupForm(GroupData groupData) {
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys(groupData.getGroupName());
        wd.findElement(By.name("group_header")).click();
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys(groupData.getGroupHeader());
        wd.findElement(By.name("group_footer")).click();
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys(groupData.getGroupFooter());
        submitGroupCreation();
    }

    private void submitGroupCreation() {
        wd.findElement(By.name("submit")).click();
    }

    protected void initGroupCreation() {
//        new WebDriverWait(wd, 30).until(ExpectedConditions.presenceOfElementLocated(By.linkText("groups"))).click();
        wd.findElement(By.name("new")).click();
    }

    protected void goToGroupPage() {
//        new WebDriverWait(wd, 30).until(ExpectedConditions.presenceOfElementLocated(By.linkText("groups"))).click();
        wd.findElement(By.linkText("groups")).click();
    }

    protected void submitContactForm() {
        wd.findElement(By.name("submit")).click();
    }

    protected void initContactForm() {
        wd.findElement(By.linkText("add new")).click();
    }

    @AfterMethod
    public void tearDown() {
        wd.quit();
    }

    protected void deleteGroup() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[5]")).click();
    }

    protected void selectGroup() {
        wd.findElement(By.name("selected[]")).click();
    }

    protected void fillContactForm(String firstname, String middlename, String lastname, String nickname) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(firstname);
        wd.findElement(By.name("middlename")).click();
        wd.findElement(By.name("middlename")).clear();
        wd.findElement(By.name("middlename")).sendKeys(middlename);
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(lastname);
        wd.findElement(By.name("nickname")).click();
        wd.findElement(By.name("nickname")).clear();
        wd.findElement(By.name("nickname")).sendKeys(nickname);
    }

    protected void deleteContact() {
        wd.findElement(By.xpath("//div[@id='content']/form[2]/div[2]/input")).click();
    }

    protected void selectContact() {
        wd.findElement(By.name("selected[]")).click();
    }

    protected void alertOk() {
        wd.switchTo().alert().accept();
    }
}
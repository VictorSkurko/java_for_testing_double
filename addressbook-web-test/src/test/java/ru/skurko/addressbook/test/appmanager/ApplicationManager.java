package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    //Здесь FirefoxDriver заменен на WebDriver т.к. это интерфейс с описанием
    // драйверов для различных браузеров. Теперь wd может принимать различные
    // типы драйверов для различных браузеров

    WebDriver wd;

    private ContactHelper contactHelper;
    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void init() {

        //Проверяем тип браузера и присваиваем wd соответствующий тип

        if (browser == BrowserType.FIREFOX) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (browser == BrowserType.CHROME) {
            wd = new ChromeDriver();
        } else if (browser == BrowserType.IE) {
            wd = new InternetExplorerDriver();
        }

//        wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        //Далее меняем FirefoxDriver на WebDriver в Helper-ах

        navigationHelper = new NavigationHelper(wd);

        groupHelper = new GroupHelper(wd);

        contactHelper = new ContactHelper(wd);

        sessionHelper = new SessionHelper(wd);

        sessionHelper.login("admin", "secret");
    }

    public void alertOk() {
        wd.switchTo().alert().accept();
    }
    public void stop() {
        wd.quit();
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }
    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
    public ContactHelper getContactHelper() {
        return contactHelper;
    }
    public SessionHelper getSessionHelper(){
        return sessionHelper;
    }
}
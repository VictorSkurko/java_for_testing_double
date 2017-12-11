package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;

    //Здесь FirefoxDriver заменен на WebDriver т.к. это интерфейс с описанием
    // драйверов для различных браузеров. Теперь wd может принимать различные
    // типы драйверов для различных браузеров

    WebDriver wd;

    private ContactHelper contactHelper;
    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private String browser;

    public ApplicationManager(String browser){
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {

        String target = System.getProperty("target", "local");
        properties.load(new FileReader
                (new File(String.format("src/test/resources/%s.properties", target))));

        //Проверяем тип браузера и присваиваем wd соответствующий тип

        if (Objects.equals(browser, BrowserType.FIREFOX)) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (Objects.equals(browser, BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (Objects.equals(browser, BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }

        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        //Далее меняем FirefoxDriver на WebDriver в Helper-ах

        wd.get(properties.getProperty("web.baseUrl"));
        navigationHelper = new NavigationHelper(wd);
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper
                .login(properties.getProperty("web.adminLogin"),
                        properties.getProperty("web.adminPassword"));
    }

    public void alertOk() {
        wd.switchTo().alert().accept();
    }
    public void stop() {
        wd.quit();
    }

    public GroupHelper group() {
        return groupHelper;
    }
    public NavigationHelper goTo() {
        return navigationHelper;
    }
    public ContactHelper contact() {
        return contactHelper;
    }
    public SessionHelper getSessionHelper(){
        return sessionHelper;
    }
}
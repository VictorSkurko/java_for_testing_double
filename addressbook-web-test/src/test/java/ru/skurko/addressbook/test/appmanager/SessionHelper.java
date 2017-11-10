package ru.skurko.addressbook.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper extends HelperBase{

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String username, String password) {
        wd.get("http://localhost/addressbook/");
        type(By.name("user"),username);
        type(By.name("pass"), password);
        click(By.xpath("//form[@id='LoginForm']/input[3]"));
    }

    public void logout() {
        click(By.linkText("Logout"));
    }
}

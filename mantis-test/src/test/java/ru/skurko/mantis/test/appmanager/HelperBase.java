package ru.skurko.mantis.test.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class HelperBase {

    protected ApplicationManager app;
    protected WebDriver wd;

    public HelperBase(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {

        click(locator);

        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) {
                wd.findElement(locator).sendKeys(file.getAbsolutePath());
            }
        }


    public static boolean isAlertPresent(WebDriver wd) { //Здесь FirefoxDriver? У нас уже везде WebDriver
        try { //Пытаемся найти окно алерта
            wd.switchTo().alert();
            return true; // Если получилось - то возвращается true
        } catch (NoAlertPresentException e) { //В противном случае выбрасывается соответствующее исключение
            return false; // И возвращается false
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}

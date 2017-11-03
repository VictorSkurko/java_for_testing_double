package ru.skurko.addressbook.test.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.skurko.addressbook.test.appmanager.ApplicationManager;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();

    @AfterMethod
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        app.init();
    }

    public ApplicationManager getApp() {
        return app;
    }
}
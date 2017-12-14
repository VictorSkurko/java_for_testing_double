package ru.skurko.addressbook.test.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.skurko.addressbook.test.appmanager.ApplicationManager;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    public ApplicationManager getApp() {
        return app;
    }
    public void verifyGroupListInUi() {

        if (Boolean.getBoolean("verifyUI")) {
        Groups dbGroups = app.db().groups();
        Groups uiGroups = app.group().all();
        assertThat(uiGroups, equalTo(dbGroups
                .stream()
                .map((g)->new GroupData()
                        .withId(g.getId())
                        .withGroupName(g.getGroupName()))
                .collect(Collectors.toSet())));
        }
    }
}
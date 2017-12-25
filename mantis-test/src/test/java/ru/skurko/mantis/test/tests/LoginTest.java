package ru.skurko.mantis.test.tests;

import org.testng.annotations.Test;
import ru.skurko.mantis.test.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.*;

public class LoginTest extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        //Проверяем что пользователь успешно залогинился
        //т.е. на странице пооявился нужный текст
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}

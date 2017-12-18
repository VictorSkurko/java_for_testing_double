package ru.skurko.mantis.test.tests;

import org.testng.annotations.Test;
import ru.skurko.mantis.test.appmanager.HttpSession;
import java.io.IOException;
import static org.testng.Assert.*;

public class LoginTest extends TestBase {

    @Test
    public void testLogin() throws IOException {

        //Создаем новую сессию
        HttpSession session = app.newSession();

        //Проверяем что пользователь успешно залогинился
        assertTrue(session.login("administrator", "root"));

        //и на странице пооявился нужный текст
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
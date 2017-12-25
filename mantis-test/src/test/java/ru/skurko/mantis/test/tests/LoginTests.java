package ru.skurko.mantis.test.tests;

import org.testng.annotations.Test;
import ru.skurko.mantis.test.appmanager.HttpSession;
import java.io.IOException;
import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
//        session.login("administrator", "root");
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
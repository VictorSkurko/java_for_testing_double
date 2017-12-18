package ru.skurko.mantis.test.tests;

import org.testng.annotations.Test;

public class RegistrationTest extends TestBase {

    @Test
    public void testRegistration() {
        app.registration().start("user00", "user00@localhost.localdomain");
    }
}

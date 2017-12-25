package ru.skurko.mantis.test.tests;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ru.lanwen.verbalregex.VerbalExpression;

import ru.skurko.mantis.test.appmanager.HttpSession;
import ru.skurko.mantis.test.model.MailMessage;
import ru.skurko.mantis.test.model.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ResetPasswordTest extends TestBase {
    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {
        app.login().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        User user = app.reset().users().stream()
                .filter(u->!app.getProperty("web.adminLogin").equals(u.getLogin()))
                .findFirst().orElse(null);
        Assert.assertNotNull(user);
        app.reset().resetPassword(user.getId());
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 60000);
        String passwordResetLink = findPasswordResetLink(mailMessages, user.getEmail());
        String password = UUID.randomUUID().toString();
        app.reset().finish(passwordResetLink, password);
        HttpSession session= app.newSession();
        Assert.assertTrue(session.login(user.getLogin(), password));
    }

    private String findPasswordResetLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages
                .stream()
                .filter((m) -> m.getTo().equals(email))
                .findFirst().get();
        VerbalExpression regex = VerbalExpression.regex()
                .find("http://")
                .nonSpace()
                .oneOrMore()
                .build();
        return regex.getText(mailMessage.getText());
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}

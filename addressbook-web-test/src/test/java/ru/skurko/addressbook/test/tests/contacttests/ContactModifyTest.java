package ru.skurko.addressbook.test.tests.contacttests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.tests.TestBase;
import java.util.Comparator;
import java.util.List;

public class ContactModifyTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactPage();
        if (app.contact().list().size() == 0) {
            app.contact().create((new ContactData()
                    .withFirstName("A")
                    .withMiddleName("A")
                    .withLastName("A")
                    .withNickName("A")
                    .withGroup("A")), true);
        }
    }

    @Test (enabled = true)
    public void testContactModify() {
        List<ContactData> before =app.contact().list();
        int index = before.size() -1;
        ContactData contact = new ContactData().withId(before.get(index).getId())
                .withFirstName("Иван")
                .withMiddleName("Иванович")
                .withLastName("Иванов")
                .withNickName("III")
                .withGroup("NewI");
        app.contact().modify(index, contact);
        app.goTo().contactPage();
        List<ContactData> after =app.contact().list();
        //Сравниваем количество контактов до и после модификации
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before,after);
    }
}
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
        app.goTo().goToContactPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact((new ContactData()
                    .withFirstName("A")
                    .withMiddleName("A")
                    .withLastName("A")
                    .withNickName("A")
                    .withGroup("A")), true);
        }
    }

    @Test (enabled = true)
    public void testContactModify() {
        List<ContactData> before =app.getContactHelper().getContactList();
        int index = before.size() -1;
        ContactData contact = new ContactData().withId(before.get(index).getId())
                .withFirstName("Иван")
                .withMiddleName("Иванович")
                .withLastName("Иванов")
                .withNickName("III")
                .withGroup("NewI");
        app.getContactHelper().modifyContact(index, contact);
        app.goTo().goToContactPage();
        List<ContactData> after =app.getContactHelper().getContactList();
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
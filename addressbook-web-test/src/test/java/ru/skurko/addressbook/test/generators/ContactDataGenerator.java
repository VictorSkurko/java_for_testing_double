package ru.skurko.addressbook.test.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.skurko.addressbook.test.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "GroupCount")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContact(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(contacts, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Нераспознанный формат: " + format);
        }
    }

    private List<ContactData> generateContact(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData()
                    .withFirstName(String.format("firstname %s", i))
                    .withMiddleName(String.format("middlename %s", i))
                    .withLastName(String.format("lastname %s", i))
                    .withNickName(String.format("nickname %s", i))
//                    .withGroup("NewI")
                    .withAddress(String.format("address %s", i))
                    .withHomePhone(String.format("homePhone %s", i))
                    .withMobilePhone(String.format("mobilePhone %s", i))
                    .withWorkPhone(String.format("workPhone %s", i))
                    .withEmail(String.format("email %s", i))
                    .withEmail2(String.format("email2 %s", i))
                    .withEmail3(String.format("email3 %s", i)));
        }
        return contacts;
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        /* Здесь и далее применяем try для того,
        чтобы не забыть закрыть файл вместо указания writer.close().
        Файл закроется сам.*/
        try (Writer writer = new FileWriter(file);) {
            writer.write(json);
        }
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        try (Writer writer = new FileWriter(file);) {
            writer.write(xml);
        }
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {

        System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file);) {
            for (ContactData contact : contacts) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                        contact.getFirstname(),
                        contact.getMiddlename(),
                        contact.getLastname(),
                        contact.getNickname(),
//                    contact.getGroup(),
                        contact.getAddress(),
                        contact.getHomePhone(),
                        contact.getMobilePhone(),
                        contact.getWorkPhone(),
                        contact.getEmail(),
                        contact.getEmail2(),
                        contact.getEmail3()));
            }
        }
    }
}

package ru.skurko.addressbook.test.appmanager;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.skurko.addressbook.test.model.ContactData;
import ru.skurko.addressbook.test.model.Contacts;
import ru.skurko.addressbook.test.model.GroupData;
import ru.skurko.addressbook.test.model.Groups;
import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
// A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Groups groups () {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData" ).list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contacts contacts () {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated is null" ).list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }
}

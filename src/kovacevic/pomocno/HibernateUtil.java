/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.io.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Marko Kovačević Ovo je Singleton
 */
public class HibernateUtil {

    private static Session session = null;

    protected HibernateUtil() {
        // Exists only to defeat instantiation.
    }

    public static Session getSession() {
        if (session == null) {
            try {
                File f = new File("hibernate.cfg.xml");
                SessionFactory sessionFactory = new Configuration().configure(f).buildSessionFactory();
                session = sessionFactory.openSession();
            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return session;
    }
}

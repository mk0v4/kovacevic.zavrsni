/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.io.File;
import kovacevic.Start;
import org.hibernate.Session;
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

//    public static Session getSession() {
//        if (session == null) {
//            try {
//                session = new Configuration().configure().buildSessionFactory().openSession();
//            } catch (Throwable ex) {
//                // Make sure you log the exception, as it might be swallowed
//                System.err.println("Initial SessionFactory creation failed." + ex);
//                throw new ExceptionInInitializerError(ex);
//            }
//        }
//        return session;
//    }
    public static Session getSession() {
        if (session == null) {
            try {
                String putanja = Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                putanja = putanja.substring(1);

                File f = new File(getParent(putanja) + File.separator + "hibernate.cfg.xml");
                session = new Configuration()
                        .configure(f)
                        .buildSessionFactory().openSession();
                //session.setCacheMode(CacheMode.REFRESH);
            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return session;
    }

    private static String getParent(String resourcePath) {
        int index = resourcePath.lastIndexOf('/');
        if (index > 0) {
            return resourcePath.substring(0, index);
        }
        return "/";
    }

}

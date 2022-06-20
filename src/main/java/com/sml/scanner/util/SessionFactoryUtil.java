package com.sml.scanner.util;

import com.sml.scanner.model.ScannedFile;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(ScannedFile.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}

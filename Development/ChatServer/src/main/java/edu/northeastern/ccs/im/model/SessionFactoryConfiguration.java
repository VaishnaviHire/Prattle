package edu.northeastern.ccs.im.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * The type Session factory configuration.
 */
public class SessionFactoryConfiguration {
    private static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {


        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }


    /**
     * Get session factory session factory.
     *
     * @return the session factory
     */
    public static SessionFactory getSessionFactory(){
        if(sessionFactoryObj == null)
            sessionFactoryObj = buildSessionFactory();
        return sessionFactoryObj;
    }
}


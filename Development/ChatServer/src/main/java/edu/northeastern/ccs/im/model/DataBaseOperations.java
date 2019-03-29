package edu.northeastern.ccs.im.model;

import edu.northeastern.ccs.im.Message;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseOperations {
    private Appendable app;

    public DataBaseOperations(StringBuilder output) {
        this.app = output;
    }

    // Method Used To Create The Hibernate's SessionFactory Object
    public static SessionFactory getSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, Service Registry Is Being Used
        ServiceRegistry serviceRegistryObj =
                new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate Session Factory Instance
        return configObj.buildSessionFactory(serviceRegistryObj);
    }

    public Object getSpecificUser(String username, String password) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        sessionObject.beginTransaction();
        try {
            Query q = sessionObject.createQuery("from User where uName = :a and password = :b");
            q.setParameter("a", username);
            q.setParameter("b", password);
            System.out.println(q.toString());
            try {
                Object a = q.uniqueResult();
                return a;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception sqlException) {
        }
        return null;
    }

    public Group1 getSpecificGrp(String grpname) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        Criteria criteria = sessionObject.createCriteria(Group1.class);
        try {
            Group1 yourObject = (Group1) criteria.add(Restrictions.eq("groupName", grpname)).uniqueResult();
//            Query q = sessionObject.createQuery("from Group1 where group_name = 'dummy' ");
            return yourObject;
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public List getAllRecords(String objectType) {
        Session sessionObj = SessionFactoryConfiguration.getSessionFactory().openSession();
        List objectList = null;
        if (objectType.matches("user")) {
            try {
                objectList = sessionObj.createQuery("FROM User").list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                objectList = sessionObj.createQuery("From Group1").list();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        sessionObj.close();
        try {
            this.app.append(String.valueOf(objectList.size()));
            this.app.append("\n");
        } catch (IOException e) {
            //DO nothing
        }
        return objectList;
    }

    public List getAllRecordsNonPrivate(String objectType) {
        Session sessionObj = SessionFactoryConfiguration.getSessionFactory().openSession();
        List objectList = null;
        try {
            if (objectType.matches("user")) {
                objectList = sessionObj.createQuery("FROM User where is_private = false").list();
            } else {
                objectList = sessionObj.createQuery("From Group1 where is_private = false").list();
            }
        } catch (Exception e) {

        } finally {
            sessionObj.close();
        }
        return objectList;
    }
    public void createRecord(Message m){

    }

}

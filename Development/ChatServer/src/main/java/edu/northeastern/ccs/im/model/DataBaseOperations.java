package edu.northeastern.ccs.im.model;

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


    public Object getSpecificUser(String username, String password) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        sessionObject.beginTransaction();

            Query q = sessionObject.createQuery("from User where uName = :a and password = :b");
            q.setParameter("a", username);
            q.setParameter("b", password);
            System.out.println(q.toString());
                Object a = q.uniqueResult();
                return a;


    }

    public Group1 getSpecificGrp(String grpname) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        Criteria criteria = sessionObject.createCriteria(Group1.class);
            Group1 yourObject = (Group1) criteria.add(Restrictions.eq("groupName", grpname)).uniqueResult();
            return yourObject;
    }

    public List getAllRecords(String objectType) {
        Session sessionObj = SessionFactoryConfiguration.getSessionFactory().openSession();
        List objectList = null;
        if (objectType.matches("user")) {

                objectList = sessionObj.createQuery("FROM User").list();

        } else {
                objectList = sessionObj.createQuery("From Group1").list();
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

            if (objectType.matches("user")) {
                objectList = sessionObj.createQuery("FROM User where is_private = false").list();
            } else {
                objectList = sessionObj.createQuery("From Group1 where is_private = false").list();
            }



            sessionObj.close();

        return objectList;
    }
//    public void createRecord(Message m){
//
//    }

}

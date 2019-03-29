package edu.northeastern.ccs.im.model;

import edu.northeastern.ccs.im.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class MessageDAO {
    private StringBuilder output;
    public MessageDAO(StringBuilder output) {
        this.output = output;
    }

    public void createMessage (Message message) {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        dataBaseOperation.createRecord(message);
    }
//    public void updateMessage (Message message) {
//        Session sessionObj = getSessionFactory().openSession();
//        //Creating Transaction Object
//        Transaction transObj = sessionObj.beginTransaction();
//        ChatterUser newUser = (ChatterUser) sessionObj.load(ChatterUser.class,
//                chatterUser.getId());
//        newUser.setName(chatterUser.getName());
//        newUser.setPassword(chatterUser.getPassword());
//        sessionObj.saveOrUpdate(newUser);
//        // Transaction Is Committed To Database
//        transObj.commit();
//        // Closing The Session Object
//        sessionObj.close();
//    }

}




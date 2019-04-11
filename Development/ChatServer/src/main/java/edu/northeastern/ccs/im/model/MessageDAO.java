package edu.northeastern.ccs.im.model;

import edu.northeastern.ccs.im.model.MessageModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

import static edu.northeastern.ccs.im.model.SessionFactoryConfiguration.getSessionFactory;

public class MessageDAO {
    private StringBuilder output;
    public MessageDAO(StringBuilder output) {
        this.output = output;
    }

    public void createMessage (MessageModel message) {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        dataBaseOperation.createRecord(message);
    }
}




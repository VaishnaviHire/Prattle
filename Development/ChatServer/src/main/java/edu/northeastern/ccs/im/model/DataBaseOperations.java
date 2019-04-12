package edu.northeastern.ccs.im.model;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * The type Data base operations.
 */
public class DataBaseOperations {
    private Appendable app;


    /**
     * Instantiates a new Data base operations.
     *
     * @param output the output
     */
    public DataBaseOperations(StringBuilder output) {
        this.app = output;
    }


    /**
     * Gets specific user.
     *
     * @param username the username
     * @param password the password
     * @return the specific user
     */
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

    public Object getSpecificUserMessages(User u) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        sessionObject.beginTransaction();
        Query q = sessionObject.createQuery("from MessageModel where senderId = :a ");
        q.setParameter("a", u.getUserId());
        Object a = q.list();
        return a;
    }

    /**
     * Gets specific grp.
     *
     * @param grpname the grpname
     * @return the specific grp
     */
    public Group getSpecificGrp(String grpname) {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        Criteria criteria = sessionObject.createCriteria(Group.class);
            Group yourObject = (Group) criteria.add(Restrictions.eq("groupName", grpname)).uniqueResult();
            return yourObject;
    }


    /**
     * Gets all records non private.
     *
     * @param objectType the object type
     * @return the all records non private
     */
    public List getAllRecordsNonPrivate(String objectType) {
        Session sessionObj = SessionFactoryConfiguration.getSessionFactory().openSession();
        List objectList = null;

            if (objectType.matches("user")) {
                objectList = sessionObj.createQuery("FROM User where is_private = false").list();
            } else {
                objectList = sessionObj.createQuery("From Group where is_private = false").list();
            }



            sessionObj.close();

        return objectList;
    }

    /**
     * Create record.
     *
     * @param object the object
     */
    public void createRecord(Object object)  {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        sessionObject.beginTransaction();
        try {
            sessionObject.save(object);
            this.app.append("Records Saved Successfully To The Database\n");
            // Committing The Transactions To The Database
            sessionObject.getTransaction().commit();
        } catch (Exception sqlException) {

            sessionObject.getTransaction().rollback();
            throw new IllegalArgumentException();
        } finally {
            sessionObject.close();
        }
    }

    /**
     * Create record.
     *
     * @param object the object
     */
    public int deleteMessage(MessageModel object)  {
        Session sessionObject = SessionFactoryConfiguration.getSessionFactory().openSession();
        sessionObject.beginTransaction();
        try {
            Query query = sessionObject.createQuery("update MessageModel set deleted = :d where messageId = :id");
            query.setParameter("d", object.isDeleted());
            query.setParameter("id",object.getMessageId());
            int result = query.executeUpdate();
            sessionObject.getTransaction().commit();
            return result;
        } catch (Exception sqlException) {
            sessionObject.getTransaction().rollback();
            throw new IllegalArgumentException();
        } finally {
            sessionObject.close();
        }
    }

}

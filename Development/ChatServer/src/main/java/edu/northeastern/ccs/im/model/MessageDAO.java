package edu.northeastern.ccs.im.model;

import java.util.List;

/**
 * The type Message dao.
 */
public class MessageDAO {
    private StringBuilder output;

    /**
     * Instantiates a new Message dao.
     *
     * @param output the output
     */
    public MessageDAO(StringBuilder output) {
        this.output = output;
    }

    /**
     * Create message.
     *
     * @param message the message
     */
    public void createMessage (MessageModel message) {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        dataBaseOperation.createRecord(message);
    }

    /**
     * Get user messages list.
     *
     * @param user the user
     * @return the list
     */
    public List<MessageModel> getUserMessages(User user){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificUserMessages(user);
        if(l!=null){
            List<MessageModel> u = (List<MessageModel>) l;
            return u;
        }else{
            return null;
        }
    }

    /**
     * Delete message boolean.
     *
     * @param m the m
     * @return the boolean
     */
    public boolean  deleteMessage(MessageModel m){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        int i = dataBaseOperation.deleteMessage(m);
        return i>0;
    }
}




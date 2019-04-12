package edu.northeastern.ccs.im.model;

import java.util.List;

public class MessageDAO {
    private StringBuilder output;
    public MessageDAO(StringBuilder output) {
        this.output = output;
    }

    public void createMessage (MessageModel message) {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        dataBaseOperation.createRecord(message);
    }
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
    public boolean  deleteMessage(MessageModel m){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        int i = dataBaseOperation.deleteMessage(m);
        return i>0;
    }
}




package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.MessageDAO;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.model.PrivateMessageModel;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * This Message is used for sending private messages between one or more recipients.
 * We choose to handle this as a separate entity than group messages for the case when you
 * are private messaging multiple people.
 */
public class PrivateMessage extends Message {

    private ArrayList<Integer> receiversList = new ArrayList<>();


    protected PrivateMessage(JSONObject json) {
        this.msgType = MessageType.PRIVATE;
        if (json.has(BODY) && json.has(RECEIVERS) && json.has(USER_ID)) {
            this.userId = json.getInt(USER_ID);
            this.messageBody = json.getString(BODY);
            JSONArray jsonUsers = json.getJSONArray(RECEIVERS);
            for (int i = 0; i < jsonUsers.length(); i++) {
                this.receiversList.add(jsonUsers.getInt(i));
            }
        }
    }


    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        for (int receiver : this.receiversList) {
            if (active.containsKey(receiver)) {
                active.get(receiver).enqueueMessage(this);
            }
        }
    }

    @Override
    public void persist() {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        MessageModel m = new PrivateMessageModel();
        m.setSenderId(this.userId);
        ((PrivateMessageModel) m).setBody(this.messageBody);
        m.setDeleted(false);
        for (int id : this.receiversList) {
            ((PrivateMessageModel) m).setReceiverId(id);
            dao.createMessage(m);
        }
    }

    @Override
    public boolean isPrivateMessage() {
        return true;
    }


    public static void stringAppendReceivers(StringBuilder builder, List<Integer> receivers) {
        if (receivers != null) {
            for (int receiver : receivers) {
                String stringInt = Integer.toString(receiver);
                builder.append(" ").append(stringInt.length()).append(" ").append(stringInt);
            }
        } else {
            builder.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.msgType.toString());

        this.appendMessageType(result);
        stringAppendReceivers(result, this.receiversList);
        this.appendBody(result);

        return result.toString();
    }

}

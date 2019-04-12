package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.BroadcastMessageModel;
import edu.northeastern.ccs.im.model.MessageDAO;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.model.PrivateMessageModel;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

/**
 * This class is used for generaly purpose messages such as error messages
 */
public class BroadcastMessage extends Message {

    private BroadcastMessage(int userId, String body) {
        this.msgType = MessageType.BROADCAST;
        this.userId = userId;
        this.messageBody = body;
    }


    public static Message makeBroadcastMessage(int senderId, String message) {
        return new BroadcastMessage(senderId, message);
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        for (ClientRunnable tt : active.values()) {
            // Do not send the message to any clients that are not ready to receive it.
            if (tt.isInitialized()) {
                tt.enqueueMessage(this);
            }
        }
    }

    @Override
    public void persist() {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        MessageModel m = new BroadcastMessageModel();
        m.setSenderId(this.userId);
        ((BroadcastMessageModel) m).setBody(this.messageBody);
        m.setDeleted(false);
        dao.createMessage(m);
    }

    @Override
    public void deleteMessage(MessageModel x) {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        x.setDeleted(true);
        dao.deleteMessage(x);
    }

    @Override
    public boolean isBroadcastMessage() {
        return true;
    }
}

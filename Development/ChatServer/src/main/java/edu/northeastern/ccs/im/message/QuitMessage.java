package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

/**
 * Used for quitting the application.
 */
public class QuitMessage extends Message {

    public QuitMessage() {
        this.msgType = MessageType.QUIT;
    }

    public QuitMessage(int userId) {
        this.msgType = MessageType.QUIT;
        this.userId = userId;
    public QuitMessage(int userId) {
        this.msgType = MessageType.QUIT;
        this.userId = userId;
    }

    public QuitMessage(JSONObject json) {
        this.msgType = MessageType.QUIT;
        if (json.has(USER_ID)) {
            this.userId = json.getInt(USER_ID);
        }
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        //send message
    }

    @Override
    public void persist() {
        //we are currently not persisting logout messages.
    }

    @Override
    public void deleteMessage(MessageModel m) {
        //cannot delete logout messages
    }

    @Override
    public boolean terminate() {
        return true;
    }
}

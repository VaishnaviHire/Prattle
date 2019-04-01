package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

/**
 * This class is used for generaly purpose messages such as error messages
 */
public class BroadcastMessage extends Message {

    public BroadcastMessage(int userId, String body) {
        this.msgType = MessageType.BROADCAST;
        this.userId = userId;
        this.body = body;
    }

    public BroadcastMessage(JSONObject json) {
        this.msgType = MessageType.BROADCAST;
        if (json.has(USER_ID) && json.has(BODY)) {
            this.body = json.getString(BODY);
            this.userId = json.getInt(USER_ID);
        }
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
    public boolean isBroadcastMessage() {
        return true;
    }
}

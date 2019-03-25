package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

public class PrivateMessage extends Message {

    private int receiver;
    private String body;


    protected PrivateMessage(String sender, JSONObject json) {
        this.msgType = MessageType.PRIVATE;
        this.msgSender = sender;
        if (json.has(BODY) && json.has(RECEIVER)) {
            this.body = json.getString(BODY);
            this.receiver = json.getInt(RECEIVER);
        }
    }


    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        active.get(this.receiver).enqueueMessage(this);
    }

    @Override
    public boolean isPrivateMessage() {
        return true;
    }
}

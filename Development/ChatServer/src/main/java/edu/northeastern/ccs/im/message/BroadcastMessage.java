package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class BroadcastMessage extends Message {

    private String text;

    public BroadcastMessage(String msgSender, String text) {
        this.msgType = MessageType.BROADCAST;
        this.msgSender = msgSender;
        this.text = text;
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
}

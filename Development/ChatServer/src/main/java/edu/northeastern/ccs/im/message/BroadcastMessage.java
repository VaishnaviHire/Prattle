package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BroadcastMessage extends Message {

    public BroadcastMessage(String msgSender, JSONObject jsonMsg) {
        this.msgSender = msgSender;

    }

    @Override
    public void send(ConcurrentLinkedQueue<ClientRunnable> active) {

    }
}

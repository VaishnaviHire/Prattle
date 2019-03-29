package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

public class QuitMessage extends Message {

    public QuitMessage(JSONObject json) {
        this.msgType = MessageType.QUIT;
    }

    public QuitMessage(int userId) {
        this.msgType = MessageType.QUIT;
        this.userId = userId;
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

    }

    @Override
    public boolean terminate() {
        return true;
    }
}

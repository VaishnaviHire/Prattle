package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;

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
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        //send message
    }

    @Override
    public boolean terminate() {
        return true;
    }
}

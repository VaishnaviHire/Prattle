package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;

import java.util.concurrent.ConcurrentMap;

public class QuitMessage extends Message {

    public QuitMessage(String msgSender) {
        this.msgType = MessageType.QUIT;

    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

    }
}

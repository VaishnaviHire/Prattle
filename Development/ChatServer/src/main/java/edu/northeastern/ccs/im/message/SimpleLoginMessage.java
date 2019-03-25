package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;

import java.util.concurrent.ConcurrentMap;

public class SimpleLoginMessage extends Message {


    protected SimpleLoginMessage(String sender) {
        this.msgSender = sender;
        this.msgType = MessageType.HELLO;
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

    }
}

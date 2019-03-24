package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.server.ClientRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleLoginMessage extends Message {


    protected SimpleLoginMessage(String sender) {
        this.msgSender = sender;
    }

    @Override
    public void send(ConcurrentLinkedQueue<ClientRunnable> active) {

    }
}

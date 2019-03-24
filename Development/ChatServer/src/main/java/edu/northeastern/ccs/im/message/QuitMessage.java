package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.server.ClientRunnable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QuitMessage extends Message {

    public QuitMessage(String msgSender) {
        super();
    }

    @Override
    public void send(ConcurrentLinkedQueue<ClientRunnable> active) {

    }
}

package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.Prattle;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

/**
 * A message the unitialized user sends to try and log in to the application.
 */
public class SimpleLoginMessage extends Message {

    private String password;

    protected SimpleLoginMessage(JSONObject json) {
        this.msgType = MessageType.HELLO;
        if (json.has(USERNAME) && json.has(PW)) {
            this.senderUsername = json.getString(USERNAME);
            this.password = json.getString(PW);
            this.userId = Prattle.getNextUserId();
        }
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

        //send message
    }

    @Override
    public boolean loginSucceeds() {

        return (this.senderUsername != null && this.password != null);

    }

    @Override
    public void persist() {
        //We are currently not persisting login messages.
    }

    @Override
    public void deleteMessage(MessageModel m) {
        //cannot delete login messages
    }

    @Override
    public boolean isInitialization() {
        return true;
    }
}

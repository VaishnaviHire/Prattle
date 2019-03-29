package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.User;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.Prattle;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentMap;

public class SimpleLoginMessage extends Message {

    private String password;

    protected SimpleLoginMessage(JSONObject json) {
        this.msgType = MessageType.HELLO;
        if (json.has(USERNAME) && json.has(PASSWORD)) {
            this.username = json.getString(USERNAME);
            this.password = json.getString(PASSWORD);
            this.userId = Prattle.getNextUserId();
        }
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

    }

    @Override
    public boolean login_succeeds() {
        //User user = UserModel.fetch(this.username, this.password);
        if (this.username != null && this.password != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isInitialization() {
        return true;
    }
}

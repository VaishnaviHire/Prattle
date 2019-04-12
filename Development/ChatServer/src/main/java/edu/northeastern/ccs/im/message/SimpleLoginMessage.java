package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.model.User;
import edu.northeastern.ccs.im.model.UserDAO;
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
            this.username = json.getString(USERNAME);
            this.password = json.getString(PW);
            this.userId = Prattle.getNextUserId();
        }
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {

    }

    @Override
    public boolean login_succeeds() {
        //User user = UserModel.fetch(this.username, this.password);
//        UserDAO u = new UserDAO(new StringBuilder());
//        User new1 = new User();
//        new1.setUName(this.getUsername());
//        User n = u.getUser(new1);
//        if (n==null){
//            return;
//        }
        if (this.username != null && this.password != null) {
            return true;
        }
        return false;
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

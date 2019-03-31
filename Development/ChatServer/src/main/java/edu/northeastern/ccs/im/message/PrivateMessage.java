package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class PrivateMessage extends Message {

    private ArrayList<Integer> receivers = new ArrayList<>();


    protected PrivateMessage(JSONObject json) {
        this.msgType = MessageType.PRIVATE;
        if (json.has(BODY) && json.has(RECEIVERS) && json.has(USERID)) {
            this.userId = json.getInt(USERID);
            this.body = json.getString(BODY);
            JSONArray jsonUsers = json.getJSONArray(RECEIVERS);
            for (int i = 0; i < jsonUsers.length(); i++) {
                this.receivers.add(jsonUsers.getInt(i));
            }
        }
    }


    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        for (int receiver : this.receivers) {
            if (active.containsKey(receiver)) {
                active.get(receiver).enqueueMessage(this);
            }
        }
    }

    @Override
    public boolean isPrivateMessage() {
        return true;
    }


    public static void stringAppendReceivers(StringBuilder builder, List<Integer> receivers) {
        if (receivers != null) {
            for (int receiver : receivers) {
                String stringInt = Integer.toString(receiver);
                builder.append(" ").append(stringInt.length()).append(" ").append(stringInt);
            }
        } else {
            builder.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.msgType.toString());

        this.appendMessageType(result);
        stringAppendReceivers(result, this.receivers);
        this.appendBody(result);

        return result.toString();
    }

}

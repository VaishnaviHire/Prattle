package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.concurrent.ConcurrentMap;

public class GroupMessage extends Message {

    private HashSet<Integer> receivers = new HashSet<>();
    private int groupId;



    public GroupMessage(JSONObject json) {
        this.msgType = MessageType.GROUP;
        if (json.has(BODY) && json.has(RECEIVERS) && json.has(USERID) && json.has(GROUPID)) {
            this.userId = json.getInt(USERID);
            this.body = json.getString(BODY);
            this.groupId = json.getInt(GROUPID);
            JSONArray jsonUsers = json.getJSONArray(RECEIVERS);
            for (int i=0; i<jsonUsers.length(); i++) {
                this.receivers.add(jsonUsers.getInt(i));
            }
        }
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        for (int receiver : receivers) {
            if (active.containsKey(receiver)) {
                active.get(receiver).enqueueMessage(this);
            }
        }
    }

    @Override
    public boolean isGroupMessage() {
        return true;
    }

}

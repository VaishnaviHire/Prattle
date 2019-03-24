package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GroupMessage extends Message {

    private HashSet<Integer> receivers;

    public GroupMessage(String sender, JSONObject json) {
        this.msgSender = sender;
        if (json.has(BODY) && json.has(RECEIVERS)) {
            this.body = json.getString(BODY);
            JSONArray jsonUsers = json.getJSONArray(RECEIVERS);
            for (int i=0; i<jsonUsers.length(); i++) {
                this.receivers.add(jsonUsers.getInt(i));
            }
        }
    }

    @Override
    public void send(ConcurrentLinkedQueue<ClientRunnable> active) {
        for (ClientRunnable runnable : active) {
            if (runnable.getUserId() == this.receivers) {
                runnable.enqueueMessage(this);
            }
        }
    }
}

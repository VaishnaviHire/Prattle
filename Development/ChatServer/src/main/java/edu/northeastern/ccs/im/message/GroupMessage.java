package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.GroupMessageModel;
import edu.northeastern.ccs.im.model.MessageDAO;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

/**
 * This class is used for Group Messaging, where we are sending a message from one user to all the
 * members of the group.
 */
public class GroupMessage extends Message {

    private ArrayList<Integer> receiversList = new ArrayList<>();
    private int groupId;


    public GroupMessage(JSONObject json) {
        this.msgType = MessageType.GROUP;
        if (json.has(BODY) && json.has(RECEIVERS) && json.has(USER_ID) && json.has(GROUP_ID)) {
            this.userId = json.getInt(USER_ID);
            this.messageBody = json.getString(BODY);
            this.groupId = json.getInt(GROUP_ID);

            JSONArray jsonUsers = json.getJSONArray(RECEIVERS);
            for (int i=0; i<jsonUsers.length(); i++) {
                this.receiversList.add(jsonUsers.getInt(i));
            }
        }
    }

    @Override
    public boolean isGroupMessage() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.msgType.toString());

        this.appendMessageType(result);
        PrivateMessage.stringAppendReceivers(result, this.receiversList);
        this.appendBody(result);

        return result.toString();
    }

    @Override
    public void send(ConcurrentMap<Integer, ClientRunnable> active) {
        for (int receiver : this.receiversList) {
            if (active.containsKey(receiver)) {
                active.get(receiver).enqueueMessage(this);
            }
        }
    }

    @Override
    public void persist() {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        MessageModel m = new GroupMessageModel();
        m.setSenderId(this.userId);
        ((GroupMessageModel) m).setBody(this.messageBody);
        ((GroupMessageModel) m).setGroupId(this.groupId);
        m.setDeleted(false);
        for (int id:this.receiversList) {
            ((GroupMessageModel) m).setReceiverIds(id);
            dao.createMessage(m);
        }
    }

}

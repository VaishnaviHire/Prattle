package serverTest.model;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.message.Message;
import edu.northeastern.ccs.im.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessagePersistanceTests {

    /**
     * uses a message model to persis a message to database for a single user.
     */
    @Test
    public void addMessageToTable() {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        MessageModel m = new PrivateMessageModel();
        ((PrivateMessageModel) m).setReceiverId(44);
        m.setSenderId(45);
        ((PrivateMessageModel) m).setBody("this is the body of the message");

        m.setDeleted(false);
        dao.createMessage(m);
    }

    /**
     * Uses a message model to persist a message into the database.
     */
    @Test
    public void addMessageToTableGroup() {
        MessageDAO dao = new MessageDAO(new StringBuilder());
        MessageModel m = new GroupMessageModel();
        ((GroupMessageModel) m).setReceiverIds(44);
        m.setSenderId(45);
        ((GroupMessageModel) m).setBody("this is the body of the message");
        ((GroupMessageModel) m).setGroupId(122);
        m.setDeleted(false);
        dao.createMessage(m);
    }

    /**
     * Persists a group message with multiple receivers.
     */
    @Test
    public void addMessageWithClassGroupMultiple() {
        JSONObject grpMsgJSON = new JSONObject();
        JSONArray receivers = new JSONArray();
        receivers.put(44);
        receivers.put(46);
        receivers.put(47);
        grpMsgJSON.put(Message.USER_ID, 45);
        grpMsgJSON.put(Message.BODY, "this is a message");
        grpMsgJSON.put(Message.RECEIVERS, receivers);
        grpMsgJSON.put(Message.GROUP_ID, 122);
        Message grpMsg = Message.makeMessage(MessageType.GROUP.toString(), grpMsgJSON);
        grpMsg.persist();
    }

    /**
     * persists a broadcast type message to the database.
     */
    @Test
    public void testBroadcastPersistence() {
        final String Msg = "this is a message";
        JSONObject bctMsg = new JSONObject();
        bctMsg.put(Message.USER_ID, 45);
        bctMsg.put(Message.BODY, Msg);
        Message broadcast = Message.makeMessage(MessageType.BROADCAST.toString(), bctMsg);
        broadcast.persist();
    }

    /**
     * persist message using persist method.
     */
    @Test
    public void addMessageWithClassPrivate() {
        JSONObject grpMsgJSON = new JSONObject();
        JSONArray receivers = new JSONArray();
        receivers.put(44);
        grpMsgJSON.put(Message.USER_ID, 45);
        grpMsgJSON.put(Message.BODY, "this is a message");
        grpMsgJSON.put(Message.RECEIVERS, receivers);
        Message grpMsg = Message.makeMessage(MessageType.PRIVATE.toString(), grpMsgJSON);
        grpMsg.persist();
    }

    /**
     * failing to create message in database
     */
    @Test
    public void addMessageWithClassPrivate1() {
        JSONObject grpMsgJSON = new JSONObject();
        JSONArray receivers = new JSONArray();
        receivers.put(44);
        grpMsgJSON.put(Message.USER_ID, -1);
        grpMsgJSON.put(Message.BODY, "this is a message");
        grpMsgJSON.put(Message.RECEIVERS, receivers);
        Message grpMsg = Message.makeMessage(MessageType.PRIVATE.toString(), grpMsgJSON);
        assertThrows(IllegalArgumentException.class,
                () -> grpMsg.persist(),
                "Expected method to throw, but it didn't");

    }

    /**
     * failing to create message in database
     */
    @Test
    public void getAUsersMessages() {
        User u = new User();
        u.setUName("user1");
        u.setUserId(1);
        MessageDAO dao = new MessageDAO(new StringBuilder());
        List<MessageModel> x = dao.getUserMessages(u);
        x.size();
    }

    /**
     * failing to create message in database
     */
    @Test
    public void getAUsersMessages1() {
        User u = new User();
        u.setUName("user2");
        u.setUserId(45);
        MessageDAO dao = new MessageDAO(new StringBuilder());
        List<MessageModel> x = dao.getUserMessages(u);

        System.out.println(x.size());
    }

    @Test
    public void updateMessage() {
        MessageModel m = new PrivateMessageModel();
        m.setMessageId(1);
        ((PrivateMessageModel) m).setBody("this is the body of the message");
        m.setSenderId(45);
        ((PrivateMessageModel) m).setReceiverId(44);
        m.setDeleted(true);
        MessageDAO dao = new MessageDAO(new StringBuilder());
        dao.deleteMessage(m);
    }
}

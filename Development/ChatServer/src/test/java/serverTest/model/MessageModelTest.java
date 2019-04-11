package serverTest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.northeastern.ccs.im.model.BroadcastMessageModel;
import edu.northeastern.ccs.im.model.GroupMessageModel;
import edu.northeastern.ccs.im.model.LoginMessageModel;
import edu.northeastern.ccs.im.model.PrivateMessageModel;

public class MessageModelTest {

  private GroupMessageModel grpMessage;
  private PrivateMessageModel pvtMessage;
  private LoginMessageModel loginMessage;
  private BroadcastMessageModel broadcastMessage;

  @BeforeEach
  void setup() {
    pvtMessage = new PrivateMessageModel();
    grpMessage = new GroupMessageModel();
    loginMessage = new LoginMessageModel();
    broadcastMessage = new BroadcastMessageModel();
  }

  @Test
  void testPrivateMessage() {
    pvtMessage.setBody("hello");
    pvtMessage.setReceiverId(1);
    pvtMessage.setUserId(2);
    assertEquals(1, pvtMessage.getReceiverId());
    assertEquals("hello", pvtMessage.getBody());
    assertEquals(2, pvtMessage.getUserId());
  }

  @Test
  void testGroupMessage() {
    grpMessage.setBody("hello");
    List<Integer> ids = new ArrayList<>();
    ids.add(1);
    ids.add(2);
    grpMessage.setReceiverIds(ids);
    grpMessage.setUserId(3);
    grpMessage.setGroupId(2);
    assertEquals(ids, grpMessage.getReceiverIds());
    assertEquals("hello", grpMessage.getBody());
    assertEquals(3, grpMessage.getUserId());
    assertEquals(2, grpMessage.getGroupId());
  }

  @Test
  void testBroadcastMessage() {
    broadcastMessage.setBody("hello");
    broadcastMessage.setUserId(2);
    assertEquals("hello", broadcastMessage.getBody());
    assertEquals(2, broadcastMessage.getUserId());
  }

  @Test
  void testLoginMessage() {
    loginMessage.setUserId(2);
    loginMessage.setUsername("yash");
    loginMessage.setPassword("yash");
    assertEquals("yash", loginMessage.getUsername());
    assertEquals("yash", loginMessage.getPassword());
    assertEquals(2, loginMessage.getUserId());
  }
}

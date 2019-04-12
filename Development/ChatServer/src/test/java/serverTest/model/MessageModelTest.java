package serverTest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.northeastern.ccs.im.model.BroadcastMessageModel;
import edu.northeastern.ccs.im.model.GroupMessageModel;
import edu.northeastern.ccs.im.model.PrivateMessageModel;

/**
 * The type Message model test.
 */
public class MessageModelTest {

  private GroupMessageModel grpMessage;
  private PrivateMessageModel pvtMessage;
  private BroadcastMessageModel broadcastMessage;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {
    pvtMessage = new PrivateMessageModel();
    grpMessage = new GroupMessageModel();
    broadcastMessage = new BroadcastMessageModel();
  }

  /**
   * Test private message.
   */
  @Test
  void testPrivateMessage() {
    pvtMessage.setBody("hello");
    pvtMessage.setReceiverId(1);
    pvtMessage.setSenderId(2);
    assertEquals(1, pvtMessage.getReceiverId());
    assertEquals("hello", pvtMessage.getBody());
    assertEquals(2, pvtMessage.getSenderId());
  }

  /**
   * Test group message.
   */
  @Test
  void testGroupMessage() {
    grpMessage.setBody("hello");
    List<Integer> ids = new ArrayList<>();
    ids.add(1);
    ids.add(2);
    grpMessage.setReceiverIds(2);
    grpMessage.setSenderId(3);
    grpMessage.setGroupId(2);
    assertEquals(2, grpMessage.getReceiverIds());
    assertEquals("hello", grpMessage.getBody());
    assertEquals(3, grpMessage.getSenderId());
    assertEquals(2, grpMessage.getGroupId());
  }

  /**
   * Test broadcast message.
   */
  @Test
  void testBroadcastMessage() {
    broadcastMessage.setBody("hello");
    broadcastMessage.setSenderId(2);
    assertEquals("hello", broadcastMessage.getBody());
    assertEquals(2, broadcastMessage.getSenderId());
  }

}

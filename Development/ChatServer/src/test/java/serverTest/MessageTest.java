package serverTest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
  private static final String Msg = "this is a message";
  private static final String name = "Shivam";

  @Test
  void testToStringBCT() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals("BCT 6 Shivam 17 this is a message", m.toString());
  }

  @org.junit.jupiter.api.Test
  void testToStringQuit() {
    Message m = Message.makeQuitMessage(name);
    assertEquals("BYE 6 Shivam 2 --", m.toString());
  }

  @org.junit.jupiter.api.Test
  void testToStringHLO() {
    Message m = Message.makeSimpleLoginMessage(name);
    assertEquals("HLO 6 Shivam 2 --", m.toString());
  }

  @org.junit.jupiter.api.Test
  void testToStringIsBCT() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertTrue(m.isBroadcastMessage());
  }
  @org.junit.jupiter.api.Test
  void testToStringIsNotBCT() {
    Message m = Message.makeSimpleLoginMessage(name);
    assertFalse(m.isBroadcastMessage());
  }
  @org.junit.jupiter.api.Test
  void testToStringIsQUIT() {
    Message m = Message.makeQuitMessage(name);
    assertTrue(m.terminate());
  }
  @org.junit.jupiter.api.Test
  void testToStringIsNotQUIT() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertFalse(m.terminate());
  }
  @org.junit.jupiter.api.Test
  void testToStringIsHLO() {
    Message m = Message.makeSimpleLoginMessage(name);
    assertTrue(m.isInitialization());
  }
  @org.junit.jupiter.api.Test
  void testToStringIsNotHLO() {
    Message m = Message.makeQuitMessage( name);
    assertFalse(m.isInitialization());
  }
  @org.junit.jupiter.api.Test
  void userGetter() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals(name,m.getName());
  }
  @org.junit.jupiter.api.Test
  void messageBodyGetter() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals(Msg,m.getText());
  }
  @org.junit.jupiter.api.Test
  void userGetterFail() {
    Message m = Message.makeBroadcastMessage(null, Msg);
    assertNull(m.getName());
  }
  @org.junit.jupiter.api.Test
  void messageBodyGetterFail() {
    Message m = Message.makeBroadcastMessage(name, null);
    assertNull(m.getText());
  }
  @org.junit.jupiter.api.Test
  void messageHelloTest()  throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method m = Message.class.getDeclaredMethod("makeHelloMessage", String.class);
    m.setAccessible(true);

    assertEquals("this is a message",((Message)m.invoke("Message",Msg)).getText());
  }

  @Test
  void makeMessageTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);
    JSONObject helloMsg = new JSONObject();
    helloMsg.put("handle", "HLO");
    helloMsg.put("sender", name);
    helloMsg.put("message", Msg);
    helloMsg.put("receivers", new ArrayList<>());
    helloMsg.put("grpName", "");

    JSONObject bctMsg = new JSONObject();
    bctMsg.put("handle", "BCT");
    bctMsg.put("sender", name);
    bctMsg.put("message", Msg);
    bctMsg.put("receivers", new ArrayList<>());
    bctMsg.put("grpName", "");

    JSONObject byeMsg = new JSONObject();
    byeMsg.put("handle", "BYE");
    byeMsg.put("sender", name);
    byeMsg.put("message", Msg);
    byeMsg.put("receivers", new ArrayList<>());
    byeMsg.put("grpName", "");

    makeMessage.setAccessible(true);
    Message bye = (Message) makeMessage.invoke("Message",byeMsg);
    Message hello = (Message) makeMessage.invoke("Message",helloMsg);
    Message broadcast = (Message) makeMessage.invoke("Message",bctMsg);

    // Initializating different types of messages
    assertFalse(bye.isInitialization());
    assertTrue(broadcast.isBroadcastMessage());
    assertTrue(hello.isInitialization());

    // Terminating the messages
    assertTrue(bye.terminate());
    assertFalse(hello.terminate());


  }
}
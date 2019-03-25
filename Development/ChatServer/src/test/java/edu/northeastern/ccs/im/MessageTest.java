package edu.northeastern.ccs.im;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.message.Message;

import java.lang.reflect.Constructor;
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
    assertEquals("BCT 6 Shivam 2 -- 17 this is a message", m.toString());
  }

  @org.junit.jupiter.api.Test
  void testToStringQuit() {
    Message m = Message.makeQuitMessage(name);
    assertEquals("BYE 6 Shivam 2 -- 2 --", m.toString());
  }

//  @org.junit.jupiter.api.Test
//  void testToStringHLO() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertEquals("HLO 6 Shivam 2 -- 2 --", m.toString());
//  }

  @org.junit.jupiter.api.Test
  void testToStringIsBCT() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertTrue(m.isBroadcastMessage());
  }

//  @org.junit.jupiter.api.Test
//  void testToStringIsNotBCT() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertFalse(m.isBroadcastMessage());
//  }

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

//  @org.junit.jupiter.api.Test
//  void testToStringIsHLO() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertTrue(m.isInitialization());
//  }

  @org.junit.jupiter.api.Test
  void testToStringIsNotHLO() {
    Message m = Message.makeQuitMessage(name);
    assertFalse(m.isInitialization());
  }

  @org.junit.jupiter.api.Test
  void userGetter() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals(name, m.getName());
  }

  @org.junit.jupiter.api.Test
  void messageBodyGetter() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals(Msg, m.getBody());
  }

  @org.junit.jupiter.api.Test
  void userGetterFail() {
    Message m = Message.makeBroadcastMessage(null, Msg);
    assertNull(m.getName());
  }

  @org.junit.jupiter.api.Test
  void messageBodyGetterFail() {
    Message m = Message.makeBroadcastMessage(name, null);
    assertNull(m.getBody());
  }

  @org.junit.jupiter.api.Test
  void messageHelloTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method m = Message.class.getDeclaredMethod("makeHelloMessage", String.class);
    m.setAccessible(true);

    assertEquals("this is a message", ((Message) m.invoke("message", Msg)).getBody());
  }

  @Test
  void makeMessageTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);
    JSONObject helloMsg = new JSONObject();
    helloMsg.put("body", Msg);
    helloMsg.put("receivers", new ArrayList<>());
    helloMsg.put("grpName", "");

    JSONObject bctMsg = new JSONObject();
    bctMsg.put("body", Msg);
    bctMsg.put("receivers", new ArrayList<>());
    bctMsg.put("grpName", "");

    JSONObject byeMsg = new JSONObject();
    byeMsg.put("body", Msg);
    byeMsg.put("receivers", new ArrayList<>());
    byeMsg.put("grpName", "");

    makeMessage.setAccessible(true);
    Message bye = Message.makeMessage(MessageType.QUIT.toString(), name,byeMsg);
    Message hello = Message.makeMessage(MessageType.HELLO.toString(), name, helloMsg);
    Message broadcast = Message.makeMessage(MessageType.BROADCAST.toString(), name, bctMsg);

    // Initializating different types of messages
    assertFalse(bye.isInitialization());
    assertTrue(broadcast.isBroadcastMessage());
    assertTrue(hello.isInitialization());

    // Terminating the messages
    assertTrue(bye.terminate());
    assertFalse(hello.terminate());


  }

  @Test
  void privateMessageTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);
    JSONArray arr = new JSONArray();
    arr.put(new JSONObject().put("name", "yash"));
    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put("body", Msg);
    pvtMsg.put("receivers", arr);
    pvtMsg.put("grpName", "");

    makeMessage.setAccessible(true);
    Message pvt = (Message) makeMessage.invoke("message", pvtMsg);

    assertTrue(pvt.isPrivateMessage());
  }

  @Test
  void message() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);
    JSONArray arr = new JSONArray();
    arr.put(new JSONObject().put("name", "yash"));
    arr.put(new JSONObject().put("name", "yash1"));
    arr.put(new JSONObject().put("name", "yash2"));
    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put("handle", "PVT");
    pvtMsg.put("message", Msg);
    pvtMsg.put("receivers", arr);
    pvtMsg.put("grpName", "grp1");

    JSONObject helloMsg = new JSONObject();
    helloMsg.put("handle", "HLO");
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
    Message pvt = (Message) makeMessage.invoke("message", pvtMsg);
    Message bye = (Message) makeMessage.invoke("message", byeMsg);
    Message hello = (Message) makeMessage.invoke("message", helloMsg);
    Message broadcast = (Message) makeMessage.invoke("message", bctMsg);
    // Initializating different types of messages
    assertEquals("PVT 2 -- 4 yash 5 yash1 5 yash2 17 this is a message",
            pvt.toString());
    assertEquals("HLO 2 -- 2 -- 2 --", hello.toString());
    assertEquals("BCT 6 Shivam 2 -- 17 this is a message", broadcast.toString());
    assertEquals("BYE 6 Shivam 2 -- 2 --", bye.toString());
    assertTrue(pvt.isPrivateMessage());
//    assertNull(pvt.getReceivingGrpName());
  }

  @Test
  void testChatLogger() {
    assertThrows(Exception.class, () -> {
      Constructor<ChatLogger> chatLoggerConstructor = ChatLogger.class.getDeclaredConstructor();
      chatLoggerConstructor.setAccessible(true);
      chatLoggerConstructor.newInstance();
    });
  }
}
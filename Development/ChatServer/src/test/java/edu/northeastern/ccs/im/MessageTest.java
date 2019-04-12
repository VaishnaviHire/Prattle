package edu.northeastern.ccs.im;

import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.Prattle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.message.Message;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageTest {
  private static final String Msg = "this is a message";
  private static final String name = "Shivam";

  @Test
  void testToStringBCT() {
    Message m = Message.makeBroadcastMessage(1234, Msg);
    assertEquals("BCT 4 1234 17 this is a message", m.toString());
  }

  @org.junit.jupiter.api.Test
  void testToStringQuit() {
    Message m = Message.makeQuitMessage(1234);
    assertEquals("BYE 4 1234 2 --", m.toString());
  }

//  @org.junit.jupiter.api.Test
//  void testToStringHLO() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertEquals("HLO 6 Shivam 2 -- 2 --", m.toString());
//  }

  @org.junit.jupiter.api.Test
  void testIsBCT() {
    Message m = Message.makeBroadcastMessage(1234, Msg);
    assertTrue(m.isBroadcastMessage());
  }

//  @org.junit.jupiter.api.Test
//  void testToStringIsNotBCT() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertFalse(m.isBroadcastMessage());
//  }

  @org.junit.jupiter.api.Test
  void testIsQUIT() {
    Message m = Message.makeQuitMessage(1234);
    assertTrue(m.terminate());
  }

  @org.junit.jupiter.api.Test
  void testIsNotQUIT() {
    Message m = Message.makeBroadcastMessage(1234, Msg);
    assertFalse(m.terminate());
  }

//  @org.junit.jupiter.api.Test
//  void testToStringIsHLO() {
//    Message m = Message.makeSimpleLoginMessage(name);
//    assertTrue(m.isInitialization());
//  }

  @org.junit.jupiter.api.Test
  void testIsNotHLO() {
    Message m = Message.makeQuitMessage(1234);
    assertFalse(m.isInitialization());
  }

  @org.junit.jupiter.api.Test
  void userGetter() {
    Message m = Message.makeBroadcastMessage(1234, Msg);
    assertEquals(1234, m.getUserId());
  }

  @org.junit.jupiter.api.Test
  void messageBodyGetter() {
    Message m = Message.makeBroadcastMessage(1234, Msg);
    assertEquals(Msg, m.getBody());
  }

//  @org.junit.jupiter.api.Test
//  void userGetterFail() {
//    Message m = Message.makeBroadcastMessage(-1, Msg);
//    assertEquals(m.getUserId(), -1);
//  }

  @org.junit.jupiter.api.Test
  void messageBodyGetterFail() {
    Message m = Message.makeBroadcastMessage(name.hashCode(), null);
    assertNull(m.getBody());
  }

//  @org.junit.jupiter.api.Test
//  void messageHelloTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//    Method m = Message.class.getDeclaredMethod("makeHelloMessage", String.class);
//    m.setAccessible(true);
//
//    assertEquals("this is a message", ((Message) m.invoke("message", Msg)).getBody());
//  }

  @Test
  void makeMessageTest() {
    JSONObject helloMsg = new JSONObject();
    helloMsg.put(Message.USERNAME, name);
    helloMsg.put("body", Msg);
    helloMsg.put("receivers", new ArrayList<>());
    helloMsg.put("grpName", "");

    JSONObject bctMsg = new JSONObject();
    bctMsg.put(Message.USERNAME, name);
    bctMsg.put("body", Msg);
    bctMsg.put("receivers", new ArrayList<>());
    bctMsg.put("grpName", "");

    JSONObject byeMsg = new JSONObject();
    byeMsg.put(Message.USERNAME, name);
    byeMsg.put("body", Msg);
    byeMsg.put("receivers", new ArrayList<>());
    byeMsg.put("grpName", "");

    Message bye = Message.makeMessage(MessageType.QUIT.toString(),byeMsg);
    Message hello = Message.makeMessage(MessageType.HELLO.toString(), helloMsg);
    Message broadcast = Message.makeMessage(MessageType.BROADCAST.toString(), bctMsg);

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
    JSONArray arr = new JSONArray();
    arr.put(new JSONObject().put("name", "yash"));
    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put("body", Msg);
    pvtMsg.put("receivers", arr);
    pvtMsg.put("grpName", "");

    Message pvt = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsg);

    assertTrue(pvt.isPrivateMessage());
  }

  @Test
  void testToString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Prattle.resetId();
    int USERID = 999;
    JSONArray receivers = new JSONArray();
    receivers.put(111);
    receivers.put(222);
    receivers.put(333);

    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put(Message.USER_ID, USERID);
    pvtMsg.put(Message.BODY, Msg);
    pvtMsg.put(Message.RECEIVERS, receivers);

    JSONObject helloMsg = new JSONObject();
    helloMsg.put(Message.USERNAME, name);
    helloMsg.put(Message.PW, "password");

    JSONObject bctMsg = new JSONObject();
    bctMsg.put(Message.USER_ID, USERID);
    bctMsg.put(Message.BODY, Msg);

    JSONObject byeMsg = new JSONObject();
    byeMsg.put(Message.USER_ID, USERID);
    byeMsg.put(Message.BODY, Msg);

    Message pvt = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsg);
    Message bye = Message.makeQuitMessage(1234);
    Message hello = Message.makeMessage(MessageType.HELLO.toString(), helloMsg);
    Message broadcast = Message.makeMessage(MessageType.BROADCAST.toString(), bctMsg);
    // Initializating different types of messages
    assertEquals("PVT 3 999 3 111 3 222 3 333 17 this is a message",
            pvt.toString());
    assertEquals("BCT 3 999 17 this is a message", broadcast.toString());
    //HLO idLength id msgLength msg
    assertEquals("HLO 1 0 2 --", hello.toString());
    assertEquals("BYE 4 1234 2 --", bye.toString());
    assertTrue(pvt.isPrivateMessage());
  }

  @Test
  void testChatLogger() {
    assertThrows(Exception.class, () -> {
      Constructor<ChatLogger> chatLoggerConstructor = ChatLogger.class.getDeclaredConstructor();
      chatLoggerConstructor.setAccessible(true);
      chatLoggerConstructor.newInstance();
    });
  }

  @Test
  void testGroupMessageToString() {
    JSONObject grpMsgJSON = new JSONObject();
    JSONArray receivers = new JSONArray();
    receivers.put(111);
    receivers.put(222);
    receivers.put(333);
    grpMsgJSON.put(Message.USER_ID, 1234);
    grpMsgJSON.put(Message.BODY, "this is a message");
    grpMsgJSON.put(Message.RECEIVERS, receivers);
    grpMsgJSON.put(Message.GROUP_ID, 9999);
    Message grpMsg = Message.makeMessage(MessageType.GROUP.toString(), grpMsgJSON);
    assertEquals("GRP 4 1234 3 111 3 222 3 333 17 this is a message", grpMsg.toString());
  }



  @Test
  void testPrivateMessageSend() {
    //Will need to be changed upon merge with persistence story
    Prattle.resetId();
    int USERID = 999;
    JSONArray receivers = new JSONArray();
    receivers.put(111);
    receivers.put(222);
    receivers.put(333);

    ClientRunnable cr1 = mock(ClientRunnable.class);
    ClientRunnable cr2 = mock(ClientRunnable.class);
    ClientRunnable cr3 = mock(ClientRunnable.class);
    ClientRunnable cr4 = mock(ClientRunnable.class);

    ConcurrentHashMap<Integer, ClientRunnable> active = new ConcurrentHashMap<>();

    active.put(111, cr1);
    active.put(222, cr2);
    active.put(333, cr3);
    active.put(444, cr4);

    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put(Message.GROUP_ID, 999);
    pvtMsg.put(Message.USER_ID, USERID);
    pvtMsg.put(Message.BODY, Msg);
    pvtMsg.put(Message.RECEIVERS, receivers);
    Message pvt = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsg);

    pvt.send(active);

    verify(cr1, times(1)).enqueueMessage(pvt);
    verify(cr2, times(1)).enqueueMessage(pvt);
    verify(cr3, times(1)).enqueueMessage(pvt);
    verify(cr4, times(0)).enqueueMessage(pvt);

  }
  @Test
  void testGroupMessageSend() {
    //Will need to be changed upon merge with persistence story
    Prattle.resetId();
    int USERID = 999;
    JSONArray receivers = new JSONArray();
    receivers.put(111);
    receivers.put(222);
    receivers.put(333);

    ClientRunnable cr1 = mock(ClientRunnable.class);
    ClientRunnable cr2 = mock(ClientRunnable.class);
    ClientRunnable cr3 = mock(ClientRunnable.class);
    ClientRunnable cr4 = mock(ClientRunnable.class);

    ConcurrentHashMap<Integer, ClientRunnable> active = new ConcurrentHashMap<>();

    active.put(111, cr1);
    active.put(222, cr2);
    active.put(333, cr3);
    active.put(444, cr4);

    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put(Message.GROUP_ID, 999);
    pvtMsg.put(Message.USER_ID, USERID);
    pvtMsg.put(Message.BODY, Msg);
    pvtMsg.put(Message.RECEIVERS, receivers);
    Message pvt = Message.makeMessage(MessageType.GROUP.toString(), pvtMsg);

    pvt.send(active);

    verify(cr1, times(1)).enqueueMessage(pvt);
    verify(cr2, times(1)).enqueueMessage(pvt);
    verify(cr3, times(1)).enqueueMessage(pvt);
    verify(cr4, times(0)).enqueueMessage(pvt);

  }
}
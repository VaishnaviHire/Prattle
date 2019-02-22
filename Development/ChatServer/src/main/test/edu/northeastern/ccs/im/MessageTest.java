package edu.northeastern.ccs.im;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
  private static final String Msg = "this is a message";
  private static final String name = "Shivam";
  @Test
  void testToStringBCT() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals("BCT 6 Shivam 17 this is a message", m.toString());
  }

  @Test
  void testToStringQuit() {
    Message m = Message.makeQuitMessage(name);
    assertEquals("BYE 6 Shivam 2 --", m.toString());
  }

  @Test
  void testToStringGeneralFail() {
    assertThrows(NullPointerException.class, () -> {
      Message m = Message.makeMessage("Handle", name, Msg);
      m.isInitialization();
    });
  }

  @Test
  void testToStringGeneralPassBCT() {
    Message m = Message.makeMessage("BCT", name, Msg);
    assertEquals("BCT 6 Shivam 17 this is a message", m.toString());
  }
  @Test
  void testToStringGeneralPassBCT1() {
    Message m = Message.makeMessage("BCT", null, Msg);
    assertEquals("BCT 2 -- 17 this is a message", m.toString());
  }

  @Test
  void testToStringGeneralPassQUIT() {
    Message m = Message.makeMessage("BYE", name, Msg);
    assertEquals("BYE 6 Shivam 2 --", m.toString());
  }

  @Test
  void testToStringGeneralPassHLO() {
    Message m = Message.makeMessage("HLO", name, Msg);
    assertEquals("HLO 6 Shivam 2 --", m.toString());
  }
  @Test
  void testToStringIsBCT() {
    Message m = Message.makeMessage("BCT", name, Msg);
    assertTrue(m.isBroadcastMessage());
  }
  @Test
  void testToStringIsNotBCT() {
    Message m = Message.makeMessage("HLO", name, Msg);
    assertFalse(m.isBroadcastMessage());
  }
  @Test
  void testToStringIsQUIT() {
    Message m = Message.makeMessage("BYE", name, Msg);
    assertTrue(m.terminate());
  }
  @Test
  void testToStringIsNotQUIT() {
    Message m = Message.makeMessage("HLO", name, Msg);
    assertFalse(m.terminate());
  }
  @Test
  void testToStringIsHLO() {
    Message m = Message.makeMessage("HLO", name, Msg);
    assertTrue(m.isInitialization());
  }
  @Test
  void testToStringIsNotHLO() {
    Message m = Message.makeMessage("BYE", name, Msg);
    assertFalse(m.isInitialization());
  }
  @Test
  void userGetter() {
    Message m = Message.makeMessage("HLO", name, Msg);
    assertEquals(name,m.getName());
  }
  @Test
  void messageBodyGetter() {
    Message m = Message.makeMessage("BCT", name, Msg);
    assertEquals(Msg,m.getText());
  }
  @Test
  void userGetterFail() {
    Message m = Message.makeMessage("HLO", null, Msg);
    assertNull(m.getName());
  }
  @Test
  void messageBodyGetterFail() {
    Message m = Message.makeMessage("BYE", name, null);
    assertNull(m.getText());
  }
  @Test
  void messageHello() {
    Message m = Message.makeHelloMessage("hello");
    assertEquals("hello",m.getText());
  }
}
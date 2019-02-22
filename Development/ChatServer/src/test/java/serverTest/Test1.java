package serverTest;

import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.Message;

import static org.junit.jupiter.api.Assertions.*;

class Test1 {
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
  void messageHello() {
    Message m = Message.makeBroadcastMessage(name, Msg);
    assertEquals("this is a message",m.getText());
  }
}
package edu.northeastern.ccs.im.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.message.Message;
import edu.northeastern.ccs.im.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;




/**
 * Test for the Prattle class
 */
public class PrattleTest {

  /**
   * Tests that the instance returned by the singleton retrieval method is actually the same object
   * each time.
   */
  @Test
  void testSingletonSameObject() {
    assertSame(Prattle.getInstance(), Prattle.getInstance());
  }

  public static Message makeSimpleLoginMessage() {
    JSONObject jsonMsg = new JSONObject();
    jsonMsg.put(Message.USERNAME, "yash");
    jsonMsg.put(Message.PW, "password");
    return Message.makeMessage(MessageType.HELLO.toString(), jsonMsg);
  }

  private Prattle testPrattle;
  private String prattleLoc;
  private String clientRunnable;
  private static Thread mainThread;

  /**
   * Initial setup, making a mock Prattle object.
   */
  @BeforeEach
  void setUp() {
    testPrattle = Prattle.getInstance();
    prattleLoc = "edu.northeastern.ccs.im.server.Prattle";
    clientRunnable = "edu.northeastern.ccs.im.server.ClientRunnable";
  }

  /**
   * Testing that the message method fires correctly when given a Queue that is not empty.
   *
   * @throws ClassNotFoundException if Prattle is not found.
   * @throws NoSuchFieldException   if the Queue field is not found.
   * @throws IllegalAccessException if the Queue field is attempted to be accessed before being made
   *                                accessible.
   */
//  @Test
//  void prattleMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//    ConcurrentHashMap<Integer, ClientRunnable> newActive = new ConcurrentHashMap<>();
//    ClientRunnable mockRunnableOne = mock(ClientRunnable.class);
//    ClientRunnable mockRunnableTwo = mock(ClientRunnable.class);
//    when(mockRunnableOne.isInitialized()).thenReturn(true);
//    when(mockRunnableTwo.isInitialized()).thenReturn(true);
//    assertNotNull(testPrattle.hashCode());
//
//
//    newActive.put(mockRunnableOne);
//    newActive.put(mockRunnableTwo);
//
//    Field prField = Class.forName(prattleLoc).getDeclaredField("active");
//    prField.setAccessible(true);
//    prField.set(testPrattle, newActive);
//
//    int activeSize = newActive.size();
//    // tests for remove client
//    Prattle.removeClient(mockRunnableOne);
//    assertEquals(newActive.size(), activeSize - 1);
//    JSONObject bctMsgJSON = new JSONObject();
//    bctMsgJSON.put("userId", "Tom".hashCode());
//    bctMsgJSON.put("body", "hi");
//    testPrattle.sendMessage(Message.makeMessage(MessageType.BROADCAST.toString(), bctMsgJSON));
//  }

//  @Test
//  void prattlePvtMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IOException, NoSuchMethodException, InvocationTargetException {
//    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
//    isReadyField.setAccessible(true);
//    isReadyField.set(testPrattle, true);
//    SocketChannel sc = SocketChannel.open();
//
//    try {
//
//      List<String> s = new ArrayList<>();
//
//      s.add("yash");
//
//      sc.configureBlocking(true);
//      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
//      NetworkConnection nc = new NetworkConnection(sc);
//
//      Field msgField = NetworkConnection.class.getDeclaredField("messages");
//      msgField.setAccessible(true);
//      ConcurrentHashMap<Integer, ClientRunnable> newActive = new ConcurrentHashMap<>();
//
//      JSONObject pvtMsgJSON = new JSONObject();
//      pvtMsgJSON.put("userId", 0);
//      pvtMsgJSON.put("body", "this is a message");
//      pvtMsgJSON.put("receivers", new JSONArray());
//
//      Message[] messages = {Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON)};
//
//      ClientRunnable RunnableOne = new ClientRunnable(nc);
//      ClientRunnable RunnableTwo = new ClientRunnable(nc);
//
//      testPrattle.hashCode();
//      JSONObject jsonMsg = new JSONObject();
//      jsonMsg.put(Message.USERNAME, "yash");
//      jsonMsg.put(Message.PASSWORD, "password");
//      Message hloMsg = Message.makeMessage(MessageType.HELLO.toString(), jsonMsg);
//      testPrattle.sendMessage(hloMsg);
//
////      RunnableOne.setUsername("shivam");
//      RunnableOne.run();
////      RunnableTwo.setUsername("yash");
////      RunnableTwo.run();
//
//      newActive.put(0, RunnableOne);
//      newActive.put(1, RunnableTwo);
//
//
//      Field prField = Class.forName(prattleLoc).getDeclaredField("active");
//      prField.setAccessible(true);
//      prField.set(testPrattle, newActive);
//
//      Field ini = RunnableTwo.getClass().getDeclaredField("initialized");
//      ini.setAccessible(true);
//      ini.set(RunnableTwo, true);
//
//      // tests for remove client
//      Message msg = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON);
//      testPrattle.sendMessage(msg);
//      Queue<Message> res = RunnableTwo.getMessageList();
//      assertEquals("PVT 6 shivam 4 yash 18 this is my message", res.remove().toString());
//
//      assertThrows(NoSuchElementException.class, () -> {
//        nc.iterator().next();
//      });
//
//
//      testPrattle.stopServer();
//      nc.close();
//
//    } catch (IOException io) {
//
//    } finally {
//      if (sc != null)
//        sc.close();
//    }
//  }


  @Test
  void prattlePrivateMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
// adding private message tests here
  }

  /**
   * Testing that the stopServer() method correctly works.
   *
   * @throws ClassNotFoundException if Prattle is not found.
   * @throws NoSuchFieldException   if the isReady field is not found.
   * @throws IllegalAccessException if the isReady field is not set to accessible before access.
   */
  @Test
  void isReadyTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);
    assertEquals("true", isReadyField.get(testPrattle).toString());

    testPrattle.stopServer();
    isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    assertEquals("false", isReadyField.get(testPrattle).toString());
  }


  /**
   * Tests the createClientThread method inside Prattle
   *
   * @throws NoSuchMethodException     if createClientThread is not found.
   * @throws InvocationTargetException if the invocation fails.
   * @throws IllegalAccessException    if the method is not made accessible before being used.
   * @throws IOException               thrown if mock channel fails
   */
  @Test
  void createThreadTest() throws IllegalAccessException, IOException, ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);
    SocketChannel sc = SocketChannel.open();

    try {

      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      NetworkConnection nc = new NetworkConnection(sc);

      Field msgField = NetworkConnection.class.getDeclaredField("messages");
      msgField.setAccessible(true);
      ConcurrentLinkedQueue<Message> messages = (ConcurrentLinkedQueue<Message>) msgField.get(nc);
      JSONObject bctMsgJSON = new JSONObject();
      bctMsgJSON.put(Message.BODY, "Hello\n How are you?");
      bctMsgJSON.put(Message.USER_ID, 1234);
      Message testMsg = Message.makeMessage(MessageType.BROADCAST.toString(), bctMsgJSON);
      messages.add(testMsg);

      // Tests for message Iterator
      assertTrue(nc.iterator().hasNext());
      assertEquals(nc.iterator().next().toString(), "BCT 4 1234 19 Hello\n How are you?");
      assertFalse(nc.iterator().hasNext());
      assertThrows(NoSuchElementException.class, () -> {
        nc.iterator().next();
      });


      testPrattle.stopServer();
      nc.close();

    } catch (IOException io) {

    } finally {
      if (sc != null)
        sc.close();
    }
  }


  /**
   * Change modifier of the given field from final to non-final and set value.
   *
   * @param f the f
   * @throws IllegalAccessException the illegal access exception
   * @throws NoSuchFieldException   the no such field exception
   */
  static void changeModifier(Field f) throws IllegalAccessException, NoSuchFieldException {
    f.setAccessible(true);

    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
    f.set(null, 6060);
  }

  /**
   * Main io exception test.
   *
   * @throws NoSuchFieldException   the no such field exception
   * @throws IllegalAccessException the illegal access exception
   */
  @Test
  public void mainIOExceptionTest() throws NoSuchFieldException, IllegalAccessException {
    Field f = ServerConstants.class.getDeclaredField("PORT");
    changeModifier(f);
    String[] argsInput = {"4545"};

    IllegalStateException thrown =
            assertThrows(IllegalStateException.class,
                    () -> Prattle.main(argsInput),
                    "Expected method to throw, but it didn't");

  }

  /**
   * Testing the Prattle main class.
   */
  @BeforeAll
  public static void testMain() {
    mainThread = new Thread(() -> {
      String[] argsInput = {"4545"};
      Prattle.main(argsInput);
    });
    mainThread.start();
  }

  /**
   * Testing the Prattle main class.
   */
  @Test
  void testPrattleMain() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);

    try {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      NetworkConnection nc = new NetworkConnection(sc);
      ClientRunnable clientRunnable = new ClientRunnable(nc);


      // test for sendMessage in NetworkConnection
      JSONObject bctMsgJSON = new JSONObject();
      bctMsgJSON.put(Message.BODY, "Hello!!");
      bctMsgJSON.put(Message.USERNAME, "Vaish");
      assertTrue(nc.sendMessage(Message.makeMessage(MessageType.BROADCAST.toString(), bctMsgJSON)));

      Prattle.stopServer();

    } catch (IOException ignored) {

    }
  }


  @Test
  public void testPrattleMainFullCover() throws ClassNotFoundException, IOException, NoSuchFieldException, IllegalAccessException {

    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, false);
    ScheduledExecutorService mockService = mock(ScheduledExecutorService.class);
    ServerSocketChannel socketChannel = ServerSocketChannel.open();
    socketChannel.configureBlocking(false);
    SocketChannel sc = SocketChannel.open();
    Thread thread = new Thread(() -> {
      try {
        socketChannel.socket().bind(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        Prattle.startServer(socketChannel);
      } catch (IOException e) {
        e.printStackTrace();
      }

    });
    thread.start();
    Prattle.stopServer();
  }


  @Test
  void prattleGrpMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {

    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);
    SocketChannel sc = SocketChannel.open();

    try {

      JSONArray s = new JSONArray();

      s.put(4567);

      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      NetworkConnection nc = new NetworkConnection(sc);

      Field msgField = NetworkConnection.class.getDeclaredField("messages");
      msgField.setAccessible(true);
      ConcurrentHashMap<Integer, ClientRunnable> newActive = new ConcurrentHashMap<>();
      JSONObject pvtMsgJSON = new JSONObject();
      pvtMsgJSON.put(Message.USER_ID, "shivam".hashCode());
      pvtMsgJSON.put(Message.RECEIVERS, s);
      pvtMsgJSON.put(Message.BODY, "this is my message");
      Message[] messages = {Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON)};


      ClientRunnable RunnableOne = new ClientRunnable(nc);
      ClientRunnable RunnableTwo = new ClientRunnable(nc);
      ClientRunnable RunnableThree = new ClientRunnable(nc);
      ClientRunnable RunnableFour = new ClientRunnable(nc);
      testPrattle.hashCode();
      RunnableOne.setUsername("shivam");
      RunnableOne.run();
      RunnableTwo.setUsername("yash");
      RunnableTwo.run();
      RunnableThree.setUsername("yash1");
      RunnableThree.run();
      RunnableFour.setUsername("yash2");
      RunnableFour.run();
      newActive.put(4567, RunnableOne);
      newActive.put(111, RunnableTwo);
      newActive.put(222, RunnableThree);
      newActive.put(333, RunnableFour);

      Field prField = Class.forName(prattleLoc).getDeclaredField("active");
      prField.setAccessible(true);
      prField.set(testPrattle, newActive);

      Field ini = RunnableTwo.getClass().getDeclaredField("initialized");
      ini.setAccessible(true);
      ini.set(RunnableTwo, true);

      Field ini1 = RunnableThree.getClass().getDeclaredField("initialized");
      ini1.setAccessible(true);
      ini1.set(RunnableThree, true);

      Field ini2 = RunnableFour.getClass().getDeclaredField("initialized");
      ini2.setAccessible(true);
      ini2.set(RunnableFour, true);
      // tests for remove client

      JSONObject grpMsgJSON = new JSONObject();
      grpMsgJSON.put(Message.USER_ID, 111);
      grpMsgJSON.put(Message.BODY, "this is a message");
      grpMsgJSON.put(Message.RECEIVERS, s);
      grpMsgJSON.put(Message.GROUP_ID, 888);

      Message.makeMessage(MessageType.GROUP.toString(), grpMsgJSON).send(newActive);
      Queue<Message> res = RunnableOne.getMessageList();
      assertEquals("GRP 3 111 4 4567 17 this is a message", res.remove().toString());
      Queue<Message> res1 = RunnableTwo.getMessageList();
      assertEquals(res1.isEmpty(), true);
      Queue<Message> res2 = RunnableThree.getMessageList();
      assertThrows(NoSuchElementException.class,
              () -> res2.remove(),
              "Expected method to throw, but it didn't");
      RunnableTwo.run();
    } catch (IOException io) {

    } finally {
      if (sc != null)
        sc.close();
    }
  }

  @Test
  void prattleGrpMessageTestPersistence() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {

    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);
    SocketChannel sc = SocketChannel.open();

    try {

      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      NetworkConnection nc = new NetworkConnection(sc);

      Field msgField = NetworkConnection.class.getDeclaredField("messages");
      msgField.setAccessible(true);
      ConcurrentHashMap<Integer, ClientRunnable> newActive = new ConcurrentHashMap<>();
      JSONArray s = new JSONArray();
      s.put(9999);
      JSONObject pvtMsgJSON = new JSONObject();
      pvtMsgJSON.put(Message.USER_ID, 1234);
      pvtMsgJSON.put(Message.RECEIVERS, s);
      pvtMsgJSON.put(Message.BODY, "this is my message");
      Message[] messages = {Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON)};


      ClientRunnable RunnableOne = new ClientRunnable(nc);
      ClientRunnable RunnableTwo = new ClientRunnable(nc);
      ClientRunnable RunnableThree = new ClientRunnable(nc);
      ClientRunnable RunnableFour = new ClientRunnable(nc);
      testPrattle.hashCode();
      RunnableOne.setUsername("shivam");
      RunnableOne.run();
      RunnableTwo.setUsername("user1");
      RunnableTwo.run();
      RunnableThree.setUsername("user2");
      RunnableThree.run();
      RunnableFour.setUsername("user3");
      RunnableFour.run();
      newActive.put(1, RunnableOne);
      newActive.put(2, RunnableTwo);
      newActive.put(3, RunnableThree);
      newActive.put(4, RunnableFour);

      Field prField = Class.forName(prattleLoc).getDeclaredField("active");
      prField.setAccessible(true);
      prField.set(testPrattle, newActive);

      Field ini = RunnableTwo.getClass().getDeclaredField("initialized");
      ini.setAccessible(true);
      ini.set(RunnableTwo, true);

      Field ini1 = RunnableThree.getClass().getDeclaredField("initialized");
      ini1.setAccessible(true);
      ini1.set(RunnableThree, true);

      Field ini2 = RunnableFour.getClass().getDeclaredField("initialized");
      ini2.setAccessible(true);
      ini2.set(RunnableFour, true);
      s.put(1111);

      int activeSize = newActive.size();
      // tests for remove client
      JSONObject grpMsgJSON = new JSONObject();
      grpMsgJSON.put(Message.USER_ID, 1234);
      grpMsgJSON.put(Message.RECEIVERS, s);
      grpMsgJSON.put(Message.BODY, "this is my message");
      testPrattle.sendMessage(Message.makeMessage(MessageType.GROUP.toString(), grpMsgJSON));

//  @Test
//  void prattleGrpMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
//
//    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
//    isReadyField.setAccessible(true);
//    isReadyField.set(testPrattle, true);
//    SocketChannel sc = SocketChannel.open();
//
//    try {
//
//      List<Integer> s = new ArrayList<>();
//
//      s.add("yash".hashCode());
//
//      sc.configureBlocking(true);
//      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
//      NetworkConnection nc = new NetworkConnection(sc);
//
//      Field msgField = NetworkConnection.class.getDeclaredField("messages");
//      msgField.setAccessible(true);
//      ConcurrentHashMap<Integer, ClientRunnable> newActive = new ConcurrentHashMap<>();
//      JSONObject pvtMsgJSON = new JSONObject();
//      pvtMsgJSON.put(Message.USERID, "shivam".hashCode());
//      pvtMsgJSON.put(Message.RECEIVERS, s);
//      pvtMsgJSON.put(Message.BODY, "this is my message");
//      Message[] messages = {Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON)};
//
//
//      ClientRunnable RunnableOne = new ClientRunnable(nc);
//      ClientRunnable RunnableTwo = new ClientRunnable(nc);
//      ClientRunnable RunnableThree = new ClientRunnable(nc);
//      ClientRunnable RunnableFour = new ClientRunnable(nc);
//      testPrattle.hashCode();
//      RunnableOne.setUsername("shivam");
//      RunnableOne.run();
//      RunnableTwo.setUsername("yash");
//      RunnableTwo.run();
//      RunnableThree.setUsername("yash1");
//      RunnableThree.run();
//      RunnableFour.setUsername("yash2");
//      RunnableFour.run();
//      newActive.put(RunnableOne.getUserId(), RunnableOne);
//      newActive.put(RunnableTwo.getUserId(), RunnableTwo);
//      newActive.put(RunnableThree.getUserId(), RunnableThree);
//      newActive.put(RunnableFour.getUserId(), RunnableFour);
//
//      Field prField = Class.forName(prattleLoc).getDeclaredField("active");
//      prField.setAccessible(true);
//      prField.set(testPrattle, newActive);
//
//      Field ini = RunnableTwo.getClass().getDeclaredField("initialized");
//      ini.setAccessible(true);
//      ini.set(RunnableTwo, true);
//
//      Field ini1 = RunnableThree.getClass().getDeclaredField("initialized");
//      ini1.setAccessible(true);
//      ini1.set(RunnableThree, true);
//
//      Field ini2 = RunnableFour.getClass().getDeclaredField("initialized");
//      ini2.setAccessible(true);
//      ini2.set(RunnableFour, true);
//      s.add("yash1".hashCode());
//
//      // tests for remove client
//      JSONObject grpMsgJSON = new JSONObject();
//      grpMsgJSON.put(Message.USERID, 1234);
//      grpMsgJSON.put(Message.BODY, "this is a message");
//      grpMsgJSON.put(Message.RECEIVERS, s);
//      testPrattle.sendMessage(Message.makeMessage(MessageType.GROUP.toString(), grpMsgJSON));

//      Queue<Message> res = RunnableTwo.getMessageList();
//      assertEquals("GRP 6 shivam 4 yash 5 yash1 18 this is my message", res.remove().toString());
//      Queue<Message> res1 = RunnableThree.getMessageList();
//      assertEquals("GRP 6 shivam 4 yash 5 yash1 18 this is my message", res1.remove().toString());
      Queue<Message> res2 = RunnableThree.getMessageList();
//      NoSuchElementException thrown =
//              assertThrows(NoSuchElementException.class,
//                      () -> res2.remove(),
//                      "Expected method to throw, but it didn't");
//      RunnableTwo.run();
    } catch (IOException io) {

    } finally {
      if (sc != null)
        sc.close();
    }
  }
//      Queue<Message> res2 = RunnableThree.getMessageList();
//      assertThrows(NoSuchElementException.class,
//              () -> res2.remove(),
//              "Expected method to throw, but it didn't");
//      RunnableTwo.run();
//    } catch (IOException io) {
//
//    } finally {
//      if (sc != null)
//        sc.close();
//    }
//  }
}




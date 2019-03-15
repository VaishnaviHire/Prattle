package edu.northeastern.ccs.im.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.NetworkConnection;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
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
  @Test
  void prattleMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    ConcurrentLinkedQueue<ClientRunnable> newActive = new ConcurrentLinkedQueue<>();
    ClientRunnable mockRunnableOne = mock(ClientRunnable.class);
    ClientRunnable mockRunnableTwo = mock(ClientRunnable.class);
    when(mockRunnableOne.isInitialized()).thenReturn(true);
    when(mockRunnableTwo.isInitialized()).thenReturn(true);
    assertNotNull(testPrattle.hashCode());


    newActive.add(mockRunnableOne);
    newActive.add(mockRunnableTwo);

    Field prField = Class.forName(prattleLoc).getDeclaredField("active");
    prField.setAccessible(true);
    prField.set(testPrattle, newActive);

    int activeSize = newActive.size();
    // tests for remove client
    Prattle.removeClient(mockRunnableOne);
    assertEquals(newActive.size(), activeSize - 1);
    testPrattle.broadcastMessage(Message.makeBroadcastMessage("Tom", "hi"));
  }

  @Test
  void prattlePvtMessageTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IOException, NoSuchMethodException, InvocationTargetException {
    Field isReadyField = Class.forName(prattleLoc).getDeclaredField("isReady");
    isReadyField.setAccessible(true);
    isReadyField.set(testPrattle, true);
    SocketChannel sc = SocketChannel.open();

    try {

      List<String> s = new ArrayList<>();

      s.add("yash");

      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(ServerConstants.HOST, ServerConstants.PORT));
      NetworkConnection nc = new NetworkConnection(sc);

      Field msgField = NetworkConnection.class.getDeclaredField("messages");
      msgField.setAccessible(true);
      ConcurrentLinkedQueue<ClientRunnable> newActive = new ConcurrentLinkedQueue<>();
      Message[] messages = {Message.makePrivateMessage("shivam", s, "this is my message")};

      ClientRunnable RunnableOne = new ClientRunnable(nc);
      ClientRunnable RunnableTwo = new ClientRunnable(nc);

      testPrattle.hashCode();
      RunnableOne.setName("shivam");
      RunnableOne.run();
      RunnableTwo.setName("yash");
      RunnableTwo.run();

      newActive.add(RunnableOne);
      newActive.add(RunnableTwo);


      Field prField = Class.forName(prattleLoc).getDeclaredField("active");
      prField.setAccessible(true);
      prField.set(testPrattle, newActive);

      Field ini = RunnableTwo.getClass().getDeclaredField("initialized");
      ini.setAccessible(true);
      ini.set(RunnableTwo, true);

      int activeSize = newActive.size();
      // tests for remove client
      Message msg = Message.makePrivateMessage("shivam", s, "this is my message");
      testPrattle.privateMessage(msg);
      Queue<Message> res = RunnableTwo.getMessageList();
      assertEquals(msg.getText(), res.remove().getText());

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
      Message testMsg = Message.makeBroadcastMessage("Vaish", "Hello\n How are you?");
      messages.add(testMsg);

      // Tests for Message Iterator
      assertTrue(nc.iterator().hasNext());
      assertEquals(nc.iterator().next().getText(), "Hello\n How are you?");
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
      assertTrue(nc.sendMessage(Message.makeBroadcastMessage("Vaish", "Hello!!")));

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
  public void testGroupMessage() throws ClassNotFoundException, IOException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);
    JSONObject grpMsg = new JSONObject();
    grpMsg.put("handle", "GRP");
    grpMsg.put("sender", "Shivam");
    grpMsg.put("message", "this is the message");
    grpMsg.put("receivers", new ArrayList<>());
    grpMsg.put("grpName", "");
    makeMessage.setAccessible(true);
    Message pvt = (Message) makeMessage.invoke("Message", grpMsg);

    Prattle.groupMessage(pvt);
  }
}




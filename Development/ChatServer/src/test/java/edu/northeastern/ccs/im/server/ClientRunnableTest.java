package edu.northeastern.ccs.im.server;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.message.Message;
import edu.northeastern.ccs.im.NetworkConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientRunnableTest {

  private NetworkConnection mockConnection;
  private ClientRunnable clientRunnable;
  private String Rnloc;

  private class MockMessageIterator implements Iterator<Message> {
    private List<Message> messages;

    MockMessageIterator() {
      messages = new ArrayList<>();
    }

    MockMessageIterator(Collection<? extends Message> messages) {
      this.messages = new ArrayList<>(messages.size());
      this.messages.addAll(messages);
    }

    @Override
    public boolean hasNext() {
      return !messages.isEmpty();
    }

    @Override
    public Message next() {
      if (messages.isEmpty()) {
        throw new NoSuchElementException();
      }
      return messages.remove(0);
    }
  }

  public static Message makeSimpleLoginMessage() {
    JSONObject jsonMsg = new JSONObject();
    jsonMsg.put(Message.USERNAME, "yash");
    jsonMsg.put(Message.PW, "password");
    return Message.makeMessage(MessageType.HELLO.toString(), jsonMsg);
  }

  @BeforeEach
  void setup() {
    mockConnection = mock(NetworkConnection.class);
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator());
    when(mockConnection.sendMessage(any())).thenReturn(true);
    clientRunnable = new ClientRunnable(mockConnection);
    Rnloc = "edu.northeastern.ccs.im.server.ClientRunnable";
  }
  @Test
  void pvtMsgTest() {

    NetworkConnection mockConnection1 = mock(NetworkConnection.class);
    when(mockConnection1.iterator()).thenReturn(new MockMessageIterator());
    when(mockConnection1.sendMessage(any())).thenReturn(true);
    ClientRunnable clientRunnable1 = new ClientRunnable(mockConnection1);
//    clientRunnable1.setUsername("shivam");
//    clientRunnable.setUsername("yash");

    JSONObject pvtMsg = new JSONObject();
    pvtMsg.put("username", "shivam");
    pvtMsg.put("body", "this is a message");
    pvtMsg.put("receivers", new String[]{"yash"});
    pvtMsg.put("grpName", "");

    Message pvt = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsg);
    assertTrue(mockConnection1.sendMessage(pvt));
  }

  @Test
  void testisInitialized() {
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  void testRun() {
    clientRunnable.run();
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  void testTerminate() {
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(20);
    ScheduledFuture<?> clientFuture = threadPool.scheduleAtFixedRate(clientRunnable, 200,
            200, TimeUnit.MILLISECONDS);
    clientRunnable.setFuture(clientFuture);
    clientRunnable.terminateClient();
    assertFalse(clientRunnable.isInitialized());
  }

//  @Test
//  public void testHandle() {
//    Message message = Message.makeBroadcastMessage("Yash", "hi");
//    assertTrue(mockConnection.sendMessage(message));
//  }

  @Test
  void testEnqueueMessage() {
    Message[] messages = {makeSimpleLoginMessage()};
    MockMessageIterator messageItr = new MockMessageIterator(Arrays.asList(messages));
    when(mockConnection.iterator()).thenReturn(messageItr);
    clientRunnable.run();

    JSONObject bctMsgJSON = new JSONObject();
    bctMsgJSON.put("userId", "yash".hashCode());
    bctMsgJSON.put("body", "hi");
    bctMsgJSON.put("receivers", new ArrayList<>());

    Message msg = Message.makeMessage(MessageType.BROADCAST.toString(), bctMsgJSON);


    JSONObject pvtMsgJSON = new JSONObject();
    pvtMsgJSON.put("username", "yash");
    pvtMsgJSON.put("body", "hi");
    pvtMsgJSON.put("receivers", new ArrayList<>());
//    pvtMsgJSON.put("grpName", "grp1");

    Message pvtmsg = Message.makeMessage(MessageType.PRIVATE.toString(), pvtMsgJSON);
    clientRunnable.enqueueMessage(msg);
    clientRunnable.enqueueMessage(pvtmsg);
    clientRunnable.run();
    assertFalse(verify(mockConnection).sendMessage(msg));
    assertFalse(verify(mockConnection).sendMessage(pvtmsg));
    assertEquals(mockConnection.iterator().hasNext(),messageItr.hasNext());
  }

  @Test
  void testGetUsername() {
    Message[] messages = {makeSimpleLoginMessage()};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertEquals("yash", clientRunnable.getUsername());
  }

  @Test
  void testSetGetName() {
    clientRunnable.setUsername("arbitrary");
    assertEquals("arbitrary", clientRunnable.getUsername());
  }

  @Test
  void testGetUserId() {
    Prattle.resetId();
    Message[] messages = {makeSimpleLoginMessage()};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertEquals(0, clientRunnable.getUserId());
  }

  @Test
  void testIsInitializedFalse() {
    assertFalse(clientRunnable.isInitialized());
  }


  @Test
  void testInitialization() {
    JSONObject bctMsgJSON = new JSONObject();
    bctMsgJSON.put("userId", 1234);
    bctMsgJSON.put("body", "hi");
    bctMsgJSON.put("receivers", new ArrayList<>());

    Message[] messages = {Message.makeMessage(MessageType.BROADCAST.toString(), bctMsgJSON)};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  void testRunUninitializedLogin() {
    Message[] messages = {makeSimpleLoginMessage()};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    assertFalse(clientRunnable.isInitialized());
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

//  @Test
//  void testRunInitializedIncomingMessages() {
//    Message[] m1 = {makeSimpleLoginMessage()};
//    Message[] m2 = {Message.makeBroadcastMessage("yash", "my message")};
//    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m1)));
//    clientRunnable.run();
//    assertTrue(clientRunnable.isInitialized());
//    //TODO: make this test better
//    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m2)));
//    clientRunnable.run();
//    assertTrue(clientRunnable.isInitialized());
//  }

//  @Test
//  void testRunInitializedInvalidMessage() {
//    Message[] m1 = {makeSimpleLoginMessage()};
//    Message[] m2 = {Message.makeBroadcastMessage("fake_user", "my message")};
//    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m1)));
//    clientRunnable.run();
//    assertTrue(clientRunnable.isInitialized());
//    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m2)));
//    clientRunnable.run();
//    assertTrue(clientRunnable.isInitialized());
//  }

  @Test
  void testRunInitializeQuitMessage() {
    Message[] message1 = {makeSimpleLoginMessage()};
    Message[] message2 = {Message.makeQuitMessage("yash".hashCode())};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(message1)));
    ClientRunnable spyClient = spy(clientRunnable);
    spyClient.setFuture(mock(ScheduledFuture.class));
    spyClient.run();
    assertTrue(spyClient.isInitialized());
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(message2)));
    spyClient.run();
    assertTrue(spyClient.isInitialized());
    verify(spyClient).terminateClient();
  }

  @Test
  void testSetFuture() {
    ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> future = threadPool.schedule(clientRunnable, 10, TimeUnit.MINUTES);
    clientRunnable.setFuture(future);
    future.cancel(true);
  }

  @Test
  void testIterator() {
    Message[] messages = {makeSimpleLoginMessage()};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    Iterator<Message> iterator = mockConnection.iterator();
    while(iterator.hasNext()) {
      Message msg = iterator.next();
      assertTrue(mockConnection.sendMessage(msg));
    }
  }

  @Test
  void testTerminateClient() {
    clientRunnable.setFuture(mock(ScheduledFuture.class));
    clientRunnable.terminateClient();
    verify(mockConnection).close();
  }

  @Test
  void testTimerBehind() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    mockConnection = mock(NetworkConnection.class);
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator());
    when(mockConnection.sendMessage(any())).thenReturn(true);
    ClientRunnable newRunnable = new NewRunnable(mockConnection);

    Field tF = Class.forName(Rnloc).getDeclaredField("timer");
    tF.setAccessible(true);
    ClientTimer newClient = new MockTimer();

    tF.set(newRunnable, newClient);
    Field newTerminateVar = Class.forName(Rnloc).getDeclaredField("terminate");
    newTerminateVar.setAccessible(true);

    assertEquals("false", newTerminateVar.get(newRunnable).toString());
    newRunnable.run();
    newTerminateVar = Class.forName(Rnloc).getDeclaredField("terminate");
    newTerminateVar.setAccessible(true);
    assertEquals("true", newTerminateVar.get(newRunnable).toString());
  }
  @Test
  void testBCTMessageSend() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    //Will need to be changed upon merge with persistence story
    Prattle.resetId();
    int USERID = 999;
    JSONArray receivers = new JSONArray();
    receivers.put(111);
    receivers.put(222);
    receivers.put(333);

    ClientRunnable cr1 = new ClientRunnable(mockConnection);
    ClientRunnable cr2 = new ClientRunnable(mockConnection);
    ClientRunnable cr3 = new ClientRunnable(mockConnection);
    ClientRunnable cr4 = new ClientRunnable(mockConnection);

    ConcurrentHashMap<Integer, ClientRunnable> active = new ConcurrentHashMap<>();

    active.put(111, cr1);
    active.put(222, cr2);
    active.put(333, cr3);
    active.put(444, cr4);

    JSONObject bctMessage = new JSONObject();
    bctMessage.put(Message.USER_ID, 444);
    bctMessage.put(Message.BODY, "this is a message");
    final Field field = Class.forName("edu.northeastern.ccs.im.server.ClientRunnable").getDeclaredField("initialized");
    field.setAccessible(true);
    Message grpMsg = Message.makeMessage(MessageType.BROADCAST.toString(), bctMessage);
    field.set(cr1,true);
    field.set(cr2,true);
    field.set(cr3,true);
    field.set(cr4,true);
    grpMsg.send(active);
    final Field field1 = Class.forName("edu.northeastern.ccs.im.server.ClientRunnable").getDeclaredField("waitingList");
    field1.setAccessible(true);
    Queue x= (Queue)field1.get(cr1);
    assertEquals(1,x.size());
    x= (Queue) field1.get(cr2);
    assertEquals(1,x.size());
    x= (Queue) field1.get(cr3);
    assertEquals(1,x.size());
    x= (Queue) field1.get(cr4);
    assertEquals(1,x.size());

  }

  @AfterEach
  void testTeardown() {
    mockConnection = null;
    clientRunnable = null;
  }

  private class MockTimer extends ClientTimer {
    @Override
    public boolean isBehind() {
      return true;
    }
  }


  private class NewRunnable extends ClientRunnable {

    NewRunnable(NetworkConnection network) {
      super(network);
    }

    @Override
    public void terminateClient() {
      // intentionally kept empty
    }
  }
}

package serverTest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.NetworkConnection;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.ClientTimer;

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

  @BeforeEach
  void setup() {
    mockConnection = mock(NetworkConnection.class);
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator());
    when(mockConnection.sendMessage(any())).thenReturn(true);
    clientRunnable = new ClientRunnable(mockConnection);
    Rnloc = "edu.northeastern.ccs.im.server.ClientRunnable";
  }
  @Test
  void pvtMsgTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    NetworkConnection mockConnection1 = mock(NetworkConnection.class);
    when(mockConnection1.iterator()).thenReturn(new MockMessageIterator());
    when(mockConnection1.sendMessage(any())).thenReturn(true);
    ClientRunnable clientRunnable1 = new ClientRunnable(mockConnection1);
    clientRunnable1.setName("shivam");
    clientRunnable.setName("yash");

    Method makeMessage = Message.class.getDeclaredMethod("makeMessage", JSONObject.class);

    JSONObject pvtMsg = new JSONObject();
    JSONArray x = new JSONArray();
    x.put(new JSONObject().put("name", "yash"));
    pvtMsg.put("handle", "PVT");
    pvtMsg.put("sender", "shivam");
    pvtMsg.put("message", "this is a message");
    pvtMsg.put("receivers", x);
    pvtMsg.put("grpName", "");

    makeMessage.setAccessible(true);
    Message pvt = (Message) makeMessage.invoke("Message",pvtMsg);
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

  @Test
  public void testHandle() {
    Message message = Message.makeBroadcastMessage("Yash", "hi");
    assertTrue(mockConnection.sendMessage(message));
  }

  @Test
  void testEnqueueMessage() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    MockMessageIterator messageItr = new MockMessageIterator(Arrays.asList(messages));
    when(mockConnection.iterator()).thenReturn(messageItr);
    clientRunnable.run();
    Message msg = Message.makeBroadcastMessage("yash", "hi");
    Message pvtmsg = Message.makePrivateMessage("yash", new ArrayList<>(), "hi");
    clientRunnable.enqueueMessage(msg);
    clientRunnable.enqueueMessage(pvtmsg);
    clientRunnable.run();
    assertFalse(verify(mockConnection).sendMessage(msg));
    assertFalse(verify(mockConnection).sendMessage(pvtmsg));
//    assertEquals(mockConnection.iterator().hasNext(),messageItr.hasNext());
  }

  @Test
  void testGetName() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertEquals("yash", clientRunnable.getName());
  }

  @Test
  void testSetGetName() {
    clientRunnable.setName("arbitrary");
    assertEquals("arbitrary", clientRunnable.getName());
  }

  @Test
  void testGetUserId() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertEquals(clientRunnable.hashCode(), clientRunnable.getUserId());
  }

  @Test
  void testIsInitializedFalse() {
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  void testIsInitializedTrue() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunInitializedTrue() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunUninitializedTrueHello() {
    Message[] messages = {Message.makeBroadcastMessage("yash", "hello")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunUninitializedLogin() {
    Message[] messages = {Message.makeSimpleLoginMessage("yash")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(messages)));
    assertFalse(clientRunnable.isInitialized());
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunUninitializedNoMessage() {
    clientRunnable.run();
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  void testRunInitializedIncomingMessages() {
    Message[] m1 = {Message.makeSimpleLoginMessage("yash")};
    Message[] m2 = {Message.makeBroadcastMessage("yash", "my message")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m1)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m2)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunInitializedInvalidMessage() {
    Message[] m1 = {Message.makeSimpleLoginMessage("yash")};
    Message[] m2 = {Message.makeBroadcastMessage("fake_user", "my message")};
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m1)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
    when(mockConnection.iterator()).thenReturn(new MockMessageIterator(Arrays.asList(m2)));
    clientRunnable.run();
    assertTrue(clientRunnable.isInitialized());
  }

  @Test
  void testRunInitializeQuitMessage() {
    Message[] message1 = {Message.makeSimpleLoginMessage("yash")};
    Message[] message2 = {Message.makeQuitMessage("yash")};
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
    Message[] messages = {Message.makeSimpleLoginMessage("yash"), Message.makeBroadcastMessage("yash", "hi")};
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

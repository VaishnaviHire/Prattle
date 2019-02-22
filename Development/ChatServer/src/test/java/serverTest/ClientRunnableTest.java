package serverTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.northeastern.ccs.im.NetworkConnection;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.Prattle;
import edu.northeastern.ccs.im.server.ServerConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClientRunnableTest {

  private ClientRunnable clientRunnable;
  private SocketChannel socketChannel;
  @Mock
  private NetworkConnection connection;

  @BeforeEach
  public void setup() {

    try {
      socketChannel = SocketChannel.open();
      socketChannel.configureBlocking(false);
      socketChannel.connect(new InetSocketAddress("localhost", 4545));

    } catch (IOException e) {
      //do nothing
    }
    connection = new NetworkConnection(socketChannel);
    clientRunnable = new ClientRunnable(connection);

  }

  @Test
  public void test1() {
    clientRunnable.setName("yash");
    assertEquals("yash", clientRunnable.getName());
  }

  @Test
  public void testisInitialized() {
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  public void testRun() {
    clientRunnable.run();
    assertFalse(clientRunnable.isInitialized());
  }

  @Test
  public void testTerminate() {
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(20);
    ScheduledFuture<?> clientFuture = threadPool.scheduleAtFixedRate(clientRunnable, 200,
            200, TimeUnit.MILLISECONDS);
    clientRunnable.setFuture(clientFuture);
    clientRunnable.terminateClient();
    assertFalse(clientRunnable.isInitialized());
  }
}

package serverTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import edu.northeastern.ccs.im.NetworkConnection;
import edu.northeastern.ccs.im.server.ClientRunnable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClientRunnableTest {

  private ClientRunnable clientRunnable;
  private SocketChannel socketChannel;
  NetworkConnection connection;

  @BeforeEach
  public void setup() {

    try {
      socketChannel = SocketChannel.open();
      socketChannel.configureBlocking(false);
      socketChannel.connect(new InetSocketAddress("localhost", 80));

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
}

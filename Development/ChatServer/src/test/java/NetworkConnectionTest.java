import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.NetworkConnection;

import static edu.northeastern.ccs.im.MessageType.BROADCAST;
import static org.junit.jupiter.api.Assertions.*;

class NetworkConnectionTest {

//  @Test
//  void sendMessage() {
//    Message m = Message.makeBroadcastMessage("shivam","this is a test message");
//    try(SocketChannel socketChannel = SocketChannel.open()) {
//
//      socketChannel.configureBlocking(false);
//      socketChannel.connect(new InetSocketAddress("localhost", 80));
//      NetworkConnection conn = new NetworkConnection(socketChannel);
//      assertTrue(conn.sendMessage(m));
//    }
//    catch(IOException e) {
//      //nothing
//    }
//  }
//  @Test
//  void sendMessage1() {
//    Message m = Message.makeBroadcastMessage("shivam", "this is a test message");
//    try(SocketChannel socketChannel = SocketChannel.open()) {
//      socketChannel.configureBlocking(true);
//      socketChannel.connect(new InetSocketAddress("localhost", 80));
//      NetworkConnection conn = new NetworkConnection(socketChannel);
//      conn.close();
//      assertThrows(IOException.class, ()->{
//        conn.sendMessage(m);
//      });
//    } catch (IOException e) {
//      //do nothing
//    }
//  }
}
//test close when it fails
//test send message when the byte size is exceeded
// make a message with null messages and null user
//
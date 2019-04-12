package edu.northeastern.ccs.im.server;

import edu.northeastern.ccs.im.ChatLogger;
import edu.northeastern.ccs.im.message.Message;
import edu.northeastern.ccs.im.NetworkConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * A network server that communicates with IM clients that connect to it. This version of the server
 * spawns a new thread to handle each client that connects to it. At this point, messages are
 * broadcast to all of the other clients. It does not send a response when the user has gone
 * off-line.
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/. It
 * is based on work originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @version 1.3
 */
public abstract class Prattle {

  private static Prattle singleTon;

  /**
   * Don't do anything unless the server is ready.
   */
  private static boolean isReady = false;

  /**
   * Collection of threads that are currently being used.
   */
  private static ConcurrentHashMap<Integer, ClientRunnable> active;

  /**
   * Collection of threads that are not yet initialized
   */
  private static ConcurrentLinkedQueue<ClientRunnable> initializing;

  /**
   * Next id to be fetched
   */
  private static int userId;


  /** All of the static initialization occurs in this "method" */
  static {
    initializing = new ConcurrentLinkedQueue<>();
    // Create the new queue of active threads.
    active = new ConcurrentHashMap<>();
    //set first userId
    userId = 0;
  }

  public static void resetId() {
    userId = 0;
  }

// add is initialized to send message methods


  public static void sendMessage(Message message) {
    message.send(active);
  }

  private Prattle() {
  }

  public static Prattle getInstance() {
    if (singleTon == null)
      singleTon = new Prattle() {
        @Override
        public int hashCode() {
          return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
          return super.equals(obj);
        }
      };

    return singleTon;
  }

  /**
   * Remove the given IM client from the list of active threads.
   *
   * @param dead Thread which had been handling all the I/O for a client who has since quit.
   */
  public static void removeClient(ClientRunnable dead) {
    // Test and see if the thread was in our list of active clients so that we
    // can remove it.
    if (active.remove(dead.getUserId()) == null) {
      ChatLogger.LOGGER.info("Could not find a thread that I tried to remove!\n");
    }
  }

  public static int getNextUserId() {
    int before = userId;
    userId++;
    return before;
  }

  /**
   * Terminates the server.
   */
  public static void stopServer() {
    isReady = false;
  }

  /**
   * Start up the threaded talk server. This class accepts incoming connections on a specific port
   * specified on the command-line. Whenever it receives a new connection, it will spawn a thread to
   * perform all of the I/O with that client. This class relies on the server not receiving too many
   * requests -- it does not include any code to limit the number of extant threads.
   *
   * @param args String arguments to the server from the command line. At present the only legal
   *             (and required) argument is the port on which this server should list.
   * @throws IOException Exception thrown if the server cannot connect to the port to which it is
   *                     supposed to listen.
   */
  public static void main(String[] args) {

    // Connect to the socket on the appropriate port to which this server connects.
    try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
      serverSocket.configureBlocking(false);
      serverSocket.socket().bind(new InetSocketAddress(ServerConstants.PORT));
      // Create the Selector with which our channel is registered.
      Prattle.startServer(serverSocket);
    } catch (IOException ex) {
      ChatLogger.LOGGER.error("Fatal error: " + ex.getMessage());
      throw new IllegalStateException(ex.getMessage());
    }

  }

  public static void startServer(ServerSocketChannel serverSocket) throws IOException {
    Selector selector = SelectorProvider.provider().openSelector();
    // Register to receive any incoming connection messages.
    serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    // Create our pool of threads on which we will execute.
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(ServerConstants.THREAD_POOL_SIZE);
    // If we get this far than the server is initialized correctly
    isReady = true;
    // Now listen on this port as long as the server is ready
    while (isReady) {
      // Check if we have a valid incoming request, but limit the time we may wait.
      while (selector.select(ServerConstants.DELAY_IN_MS) != 0) {
        // Get the list of keys that have arrived since our last check
        Set<SelectionKey> acceptKeys = selector.selectedKeys();
        // Now iterate through all of the keys
        Iterator<SelectionKey> it = acceptKeys.iterator();
        while (it.hasNext()) {
          // Get the next key; it had better be from a new incoming connection
          SelectionKey key = it.next();
          it.remove();
          // Assert certain things I really hope is true
          assert key.isAcceptable();
          if (key.channel() != serverSocket) {
            ChatLogger.LOGGER.error("Should be : key.channel() == serverSocket");
          }

          // Create new thread to handle client for which we just received request.
          createClientThread(serverSocket, threadPool);
        }
      }
    }
  }

  /**
   * Create a new thread to handle the client for which a request is received.
   *
   * @param serverSocket The channel to use.
   * @param threadPool   The thread pool to add client to.
   */
  private static void createClientThread(ServerSocketChannel serverSocket, ScheduledExecutorService threadPool) {
    try {
      // Accept the connection and create a new thread to handle this client.
      SocketChannel socket = serverSocket.accept();
      // Make sure we have a connection to work with.
      if (socket != null) {
        NetworkConnection connection = new NetworkConnection(socket);
        ClientRunnable tt = new ClientRunnable(connection);
        // Add the thread to the queue of active threads

        initializing.add(tt);
        // Have the client executed by our pool of threads.
        ScheduledFuture<?> clientFuture = threadPool.scheduleAtFixedRate(tt, ServerConstants.CLIENT_CHECK_DELAY,
                ServerConstants.CLIENT_CHECK_DELAY, TimeUnit.MILLISECONDS);
        tt.setFuture(clientFuture);
      }
    } catch (AssertionError ae) {
      ChatLogger.LOGGER.error("Caught Assertion: " + ae.toString());
    } catch (IOException e) {
      ChatLogger.LOGGER.error("Caught Exception: " + e.toString());
    }
  }

  /**
   * Takes the runnable out of initializing and puts into active map
   *
   * @param clientRunnable the client runnable we are activating
   */
  public static void activate(ClientRunnable clientRunnable) {
    if (initializing.remove(clientRunnable)) {
      active.put(clientRunnable.getUserId(), clientRunnable);
    }
  }
}
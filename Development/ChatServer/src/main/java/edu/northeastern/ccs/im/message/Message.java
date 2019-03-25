package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Each instance of this class represents a single transmission by our IM clients.
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International
 * License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/. It
 * is based on work originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @version 1.3
 */
public abstract class Message {

  /**
   * The string sent when a field is null.
   */
  private static final String NULL_OUTPUT = "--";

  /** The length of the message handle. */
  public static final int HANDLE_LENGTH = 3;


  protected static final String BODY = "body";
  protected static final String RECEIVER = "receiver";
  protected static final String RECEIVERS = "receivers";


  /**
   * The handle of the message.
   */
  protected MessageType msgType;

  /**
   * The first argument used in the message. This will be the sender's identifier.
   */
  protected String msgSender;

  /**
   * The third argument used in the message.
   */
  protected String body;


  /**
   * Given a handle, name and jsonMsg, return the appropriate message instance or an instance from a
   * subclass of message.
   *
   * @param jsonMsg JSONObject containing the information.
   * @param msgSender the sender of the message
   * @param handle the message type
   * @return Instance of message (or its subclasses) representing the handle, name, & text.
   */
  public static Message makeMessage(String handle, String msgSender, JSONObject jsonMsg) {
    if (handle.compareTo(MessageType.QUIT.toString()) == 0) {
      return new QuitMessage(msgSender);
    } else if (handle.compareTo(MessageType.HELLO.toString()) == 0) {
      return new SimpleLoginMessage(msgSender);
    } else if ((handle.compareTo(MessageType.PRIVATE.toString()) == 0)) {
      return new PrivateMessage(msgSender, jsonMsg);
    } else if (handle.compareTo(MessageType.GROUP.toString()) == 0) {
      return new GroupMessage(msgSender, jsonMsg);
    }
    else {
      //should we throw error?????
      return null;
    }
  }

  /**
   * Create a new message to continue the logout process.
   *
   * @param msgSender The name of the client that sent the quit message.
   * @return Instance of message that specifies the process is logging out.
   */
  public static Message makeQuitMessage(String msgSender) {
    return new QuitMessage(msgSender);
  }

  public static Message makeBroadcastMessage(String sender, String message) {
    return new BroadcastMessage(sender, message);
  }

  /**
   * Return the name of the sender of this message.
   *
   * @return String specifying the name of the message originator.
   */
  public String getName() {
    return msgSender;
  }

  /**
   * Return the body of this message.
   *
   * @return String equal to the text sent by this message.
   */
  public String getBody() {
    return body;
  }

  /**
   * Determine if this message is broadcasting text to everyone.
   *
   * @return True if the message is a broadcast message; false otherwise.
   */
  public boolean isBroadcastMessage() {
    return false;
  }

  /**
   * Determine if this message is private message or not.
   *
   * @return True if the message is a private message, false otherwise.
   */
  public boolean isPrivateMessage() {
    return false;
  }

  /**
   * Determine if this message is group message or not.
   *
   * @return True if the message is a group message, false otherwise.
   */
  public boolean isGroupMessage() {
    return false;
  }

  /**
   * Determine if this message is sent by a new client to log-in to the server.
   *
   * @return True if the message is an initialization message; false otherwise
   */
  public boolean isInitialization() {
    return false;
  }

  /**
   * Determine if this message is a message signing off from the IM server.
   *
   * @return True if the message is sent when signing off; false otherwise
   */
  public boolean terminate() {
    return false;
  }

  /**
   * Representation of this message as a String. This begins with the message handle and then
   * contains the length (as an integer) and the value of the next two arguments.
   *
   * @return Representation of this message as a String.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(msgType.toString());
    if (msgSender != null) {
      result.append(" ").append(msgSender.length()).append(" ").append(msgSender);
    } else {
      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
//    if (msgReceivers != null) {
//      for (String msgReceiver : msgReceivers) {
//        result.append(" ").append(msgReceiver.length()).append(" ").append(msgReceiver);
//      }
//    } else {
//      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
//    }
    if (this.body != null) {
      result.append(" ").append(this.body.length()).append(" ").append(this.body);
    } else {
      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
    return result.toString();
  }

  public abstract void send(ConcurrentMap<Integer, ClientRunnable> active);
}

package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

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
  protected static final String NULL_OUTPUT = "--";

  /** The length of the message handle. */
  public static final int HANDLE_LENGTH = 3;


  public static final String BODY = "body";
  public static final String GROUP_ID = "groupId";
  public static final String RECEIVERS = "receivers";
  public static final String USERNAME = "username";
  public static final String PW = "password";
  public static final String USER_ID   = "userId";



  /**
   * The handle of the message.
   */
  protected MessageType msgType;

  /**
   * The sender's userId
   */
  protected int userId = -1;



  /**
   * The third argument used in the message.
   */
  protected String body;

  /**
   * The sender's username
   */
  protected String username;


  /**
   * Given a handle, name and jsonMsg, return the appropriate message instance or an instance from a
   * subclass of message.
   *
   * @param jsonMsg JSONObject containing the information.
   * @param handle the message type
   * @return Instance of message (or its subclasses) representing the handle, name, & text.
   */
  public static Message makeMessage(String handle, JSONObject jsonMsg) {

    if (handle.compareTo(MessageType.QUIT.toString()) == 0) {
      return new QuitMessage(jsonMsg);
    } else if (handle.compareTo(MessageType.HELLO.toString()) == 0) {
      return new SimpleLoginMessage(jsonMsg);
    } else if ((handle.compareTo(MessageType.PRIVATE.toString()) == 0)) {
      return new PrivateMessage(jsonMsg);
    } else if (handle.compareTo(MessageType.BROADCAST.toString()) == 0) {
      return new BroadcastMessage(jsonMsg);
    } else if (handle.compareTo(MessageType.GROUP.toString()) == 0) {
      return new GroupMessage(jsonMsg);
    }
    else {
      //should we throw error?????
      return null;
    }
  }

  /**
   * Create a new message to continue the logout process.
   *
   * @param userId The id of the client that sent the quit message.
   * @return Instance of message that specifies the process is logging out.
   */
  public static Message makeQuitMessage(int userId) {
    return new QuitMessage(userId);
  }

  public static Message makeBroadcastMessage(int senderId, String message) {
    return new BroadcastMessage(senderId, message);
  }

  /**
   * Return the id of the sender of this message.
   *
   * @return int specifying the name of the message originator.
   */
  public int getUserId() {
    return this.userId;
  }


  /**
   * Return the username of the sender of this message.
   *
   * @return String specifying the name of the message originator.
   */
  public String getUsername() {
    return this.username;
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




  protected void appendMessageType(StringBuilder builder) {
    if (userId != -1) {
      builder.append(" ").append(Integer.toString(userId).length()).append(" ").append(userId);
    } else {
      builder.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
  }


  protected void appendBody(StringBuilder builder) {
    if (this.body != null) {
      builder.append(" ").append(this.body.length()).append(" ").append(this.body);
    } else {
      builder.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
  }


  /**
   * Representation of this message as a String. This begins with the message handle and then
   * contains the length (as an integer) and the value of the next two arguments.
   *
   * @return Representation of this message as a String.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(this.msgType.toString());
    this.appendMessageType(result);
//    if (msgReceivers != null) {
//      for (String msgReceiver : msgReceivers) {
//        result.append(" ").append(msgReceiver.length()).append(" ").append(msgReceiver);
//      }
//    } else {
//      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
//    }
    this.appendBody(result);
    return result.toString();
  }

  public abstract void send(ConcurrentMap<Integer, ClientRunnable> active);

  public boolean login_succeeds(){ return false; }
}

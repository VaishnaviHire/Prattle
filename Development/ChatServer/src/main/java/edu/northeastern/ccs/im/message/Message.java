package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.server.ClientRunnable;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
   static final int HANDLE_LENGTH = 3;


  protected final String BODY = "body";
  protected final String RECEIVER = "receiver";
  protected final String RECEIVERS = "receivers";


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
   * Create a new message to continue the logout process.
   *
   * @param myName The name of the client that sent the quit message.
   * @return Instance of message that specifies the process is logging out.
   */
  public static Message makeQuitMessage(String myName) {
    return null;//new Message(MessageType.QUIT, myName, null);
  }

  /**
   * Create a new message broadcasting an announcement to the world.
   *
   * @param myName Name of the sender of this very important missive.
   * @param text   Text of the message that will be sent to all users
   * @return Instance of message that transmits text to all logged in users.
   */
  public static Message makeBroadcastMessage(String myName, String text) {
    return null;// new Message(MessageType.BROADCAST, myName, null, null, text);
  }

  /**
   * Create a new private message.
   *
   * @param myName Name of the sender.
   * @param receiversName Name of the receivers.
   * @param text message text.
   * @return
   */
  public static Message makePrivateMessage(String myName, List<String> receiversName, String text) {
    return null;//new Message(MessageType.PRIVATE, myName, receiversName, null, text);
  }

  /**
   * Create a new Group message.
   *
   * @param myName Name of the sender.
   * @param grpName Name of the group.
   * @param text message text.
   * @return return message of type group.
   */
  public static Message makeGroupMessage(String myName, List<String> msgReceivers, String grpName, String text) {
    return null;//new Message(MessageType.GROUP, myName, msgReceivers, grpName, text);
  }

  /**
   * Create a new message stating the name with which the user would like to login.
   *
   * @param text Name the user wishes to use as their screen name.
   * @return Instance of message that can be sent to the server to try and login.
   */
  protected static Message makeHelloMessage(String text) {
    return null;//new Message(MessageType.HELLO, null, text);
  }

  /**
   * Given a handle, name and text, return the appropriate message instance or an instance from a
   * subclass of message.
   *
   * @param jsonMsg JSONObject containing the information.
   * @return Instance of message (or its subclasses) representing the handle, name, & text.
   */
  protected static Message makeMessage(String handle, String msgSender, JSONObject jsonMsg) {
    String grpName;

//    List<String> receiversName = null;
//    if (jsonMsg.has("receivers")) {
//      receiversName = new ArrayList<>();
//      JSONArray arr = jsonMsg.getJSONArray("receivers");
//      for (int i = 0; i < arr.length(); i++) {
//        receiversName.add(arr.getJSONObject(i).getString("name"));
//      }
//    }
//
//    if (jsonMsg.has("grpName")) {
//      grpName = jsonMsg.getString("grpName");
//    } else {
//      grpName = null;
//    }

    if (handle.compareTo(MessageType.QUIT.toString()) == 0) {
      return new QuitMessage(msgSender);
      //makeQuitMessage(srcName);
    } else if (handle.compareTo(MessageType.HELLO.toString()) == 0) {
      return new SimpleLoginMessage(msgSender);//makeSimpleLoginMessage(srcName);
    } else if ((handle.compareTo(MessageType.PRIVATE.toString()) == 0)) {
      return new PrivateMessage(msgSender, jsonMsg);
    } else if (handle.compareTo(MessageType.BROADCAST.toString()) == 0) {
      return new BroadcastMessage(msgSender, jsonMsg);//makeBroadcastMessage(srcName, text);
    } else if (handle.compareTo(MessageType.GROUP.toString()) == 0) {
      return new GroupMessage(msgSender, jsonMsg);//makeGroupMessage(srcName, receiversName, grpName, text);
    }
    else {
      //should we throw error?????
      return null;
    }
  }

  /**
   * Create a new message for the early stages when the user logs in without all the special stuff.
   *
   * @param myName Name of the user who has just logged in.
   * @return Instance of message specifying a new friend has just logged in.
   */
//  public static Message makeSimpleLoginMessage(String myName) {
//    return new Message(MessageType.HELLO, myName);
//  }

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
    return false;//(msgType == MessageType.BROADCAST);
  }

  /**
   * Determine if this message is private message or not.
   *
   * @return True if the message is a private message, false otherwise.
   */
  public boolean isPrivateMessage() {
    return false;//(msgType == MessageType.PRIVATE);
  }

  /**
   * Determine if this message is group message or not.
   *
   * @return True if the message is a group message, false otherwise.
   */
  public boolean isGroupMessage() {
    return false;//(msgType == MessageType.GROUP);
  }

  /**
   * Determine if this message is sent by a new client to log-in to the server.
   *
   * @return True if the message is an initialization message; false otherwise
   */
  public boolean isInitialization() {
    return false;//(msgType == MessageType.HELLO);
  }

  /**
   * Determine if this message is a message signing off from the IM server.
   *
   * @return True if the message is sent when signing off; false otherwise
   */
  public boolean terminate() {
    return false;//(msgType == MessageType.QUIT);
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

  public abstract void send(ConcurrentLinkedQueue<ClientRunnable> active);
}

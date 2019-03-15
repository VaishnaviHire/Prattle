package edu.northeastern.ccs.im;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
public class Message {

  /**
   * The string sent when a field is null.
   */
  private static final String NULL_OUTPUT = "--";

  /**
   * The handle of the message.
   */
  private MessageType msgType;

  /**
   * The first argument used in the message. This will be the sender's identifier.
   */
  private String msgSender;

  /**
   * The second argument used in the message.
   */
  private List<String> msgReceivers;

  /**
   * The third argument used in the message.
   */
  private String receivingGrpName;
  /**
   * The third argument used in the message.
   */
  private String msgText;

  /**
   * Create a new message that contains actual IM text. The type of distribution is defined by the
   * handle and we must also set the name of the message sender, message recipient, and the text to
   * send.
   *
   * @param handle  Handle for the type of message being created.
   * @param srcName Name of the individual sending this message
   * @param text    Text of the instant message
   */
  private Message(MessageType handle, String srcName, String text) {
    this.msgType = handle;
    // Save the properly formatted identifier for the user sending the
    // message.
    this.msgSender = srcName;
    // Save the text of the message.
    this.msgText = text;
  }

  private Message(MessageType handle, String srcName, List<String> msgReceivers, String grpName, String text) {
    this.msgType = handle;
    this.msgSender = srcName;
    this.msgReceivers = msgReceivers;
    this.receivingGrpName = grpName;
    this.msgText = text;
  }

  /**
   * Create a new message that contains a command sent the server that requires a single argument.
   * This message contains the given handle and the single argument.
   *
   * @param handle  Handle for the type of message being created.
   * @param srcName Argument for the message; at present this is the name used to log-in to the IM
   *                server.
   */
  private Message(MessageType handle, String srcName) {
    this(handle, srcName, null);
  }

  /**
   * Create a new message to continue the logout process.
   *
   * @param myName The name of the client that sent the quit message.
   * @return Instance of Message that specifies the process is logging out.
   */
  public static Message makeQuitMessage(String myName) {
    return new Message(MessageType.QUIT, myName, null);
  }

  /**
   * Create a new message broadcasting an announcement to the world.
   *
   * @param myName Name of the sender of this very important missive.
   * @param text   Text of the message that will be sent to all users
   * @return Instance of Message that transmits text to all logged in users.
   */
  public static Message makeBroadcastMessage(String myName, String text) {
    return new Message(MessageType.BROADCAST, myName, null, null, text);
  }

  /**
   * Create a new private message.
   *
   * @param myName Name of the sender.
   * @param receiversName Name of the receivers.
   * @param text Message text.
   * @return
   */
  public static Message makePrivateMessage(String myName, List<String> receiversName, String text) {
    return new Message(MessageType.PRIVATE, myName, receiversName, null, text);
  }

  /**
   * Create a new Group message.
   *
   * @param myName Name of the sender.
   * @param grpName Name of the group.
   * @param text Message text.
   * @return
   */
  public static Message makeGroupMessage(String myName, String grpName, String text) {
    return new Message(MessageType.GROUP, myName, null, grpName, text);
  }

  /**
   * Create a new message stating the name with which the user would like to login.
   *
   * @param text Name the user wishes to use as their screen name.
   * @return Instance of Message that can be sent to the server to try and login.
   */
  protected static Message makeHelloMessage(String text) {
    return new Message(MessageType.HELLO, null, text);
  }

  /**
   * Given a handle, name and text, return the appropriate message instance or an instance from a
   * subclass of message.
   *
   * @param serverRequest JSONObject containing the information.
   * @return Instance of Message (or its subclasses) representing the handle, name, & text.
   */
  protected static Message makeMessage(JSONObject serverRequest) {
    String handle = serverRequest.getString("handle");
    String srcName = serverRequest.getString("sender");
    JSONArray arr = serverRequest.getJSONArray("receivers");
    List<String> receiversName = new ArrayList<String>();
    for(int i = 0; i < arr.length(); i++){
      receiversName.add(arr.getJSONObject(i).getString("name"));
    }
    String grpName = serverRequest.getString("grpName");
    String text = serverRequest.getString("message");
    Message result = null;
    if (handle.compareTo(MessageType.QUIT.toString()) == 0) {
      result = makeQuitMessage(srcName);
    } else if (handle.compareTo(MessageType.HELLO.toString()) == 0) {
      result = makeSimpleLoginMessage(srcName);
    } else if ((handle.compareTo(MessageType.PRIVATE.toString()) == 0)) {
      result = makePrivateMessage(srcName, receiversName, text);
    } else if (handle.compareTo(MessageType.BROADCAST.toString()) == 0) {
      result = makeBroadcastMessage(srcName, text);
    } else if (handle.compareTo(MessageType.GROUP.toString()) == 0) {
      result = makeGroupMessage(srcName, grpName, text);
    }
    return result;
  }

  /**
   * Create a new message for the early stages when the user logs in without all the special stuff.
   *
   * @param myName Name of the user who has just logged in.
   * @return Instance of Message specifying a new friend has just logged in.
   */
  public static Message makeSimpleLoginMessage(String myName) {
    return new Message(MessageType.HELLO, myName);
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
   * Return the name of the name of the receivers.
   *
   * @return List of Strings specifying the name of the message receiver.
   */
  public List<String> getMsgReceivers() {
    return msgReceivers;
  }

  /**
   * Return the name of the group.
   *
   * @return String specifying the name of the group.
   */
  public String getReceivingGrpName() {
    return receivingGrpName;
  }

  /**
   * Return the text of this message.
   *
   * @return String equal to the text sent by this message.
   */
  public String getText() {
    return msgText;
  }

  /**
   * Determine if this message is broadcasting text to everyone.
   *
   * @return True if the message is a broadcast message; false otherwise.
   */
  public boolean isBroadcastMessage() {
    return (msgType == MessageType.BROADCAST);
  }

  /**
   * Determine if this message is private message or not.
   *
   * @return True if the message is a private message, false otherwise.
   */
  public boolean isPrivateMessage() {
    return (msgType == MessageType.PRIVATE);
  }

  /**
   * Determine if this message is group message or not.
   *
   * @return True if the message is a group message, false otherwise.
   */
  public boolean isGroupMessage() {
    return (msgType == MessageType.GROUP);
  }

  /**
   * Determine if this message is sent by a new client to log-in to the server.
   *
   * @return True if the message is an initialization message; false otherwise
   */
  public boolean isInitialization() {
    return (msgType == MessageType.HELLO);
  }

  /**
   * Determine if this message is a message signing off from the IM server.
   *
   * @return True if the message is sent when signing off; false otherwise
   */
  public boolean terminate() {
    return (msgType == MessageType.QUIT);
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
    if (msgReceivers != null) {
      for (String msgReceiver : msgReceivers) {
        result.append(" ").append(msgReceiver.length()).append(" ").append(msgReceiver);
      }
    } else {
      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
    if (receivingGrpName != null) {
      result.append(" ").append(receivingGrpName.length()).append(" ").append(receivingGrpName);
    } else {
      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
    if (msgText != null) {
      result.append(" ").append(msgText.length()).append(" ").append(msgText);
    } else {
      result.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
    return result.toString();
  }
}

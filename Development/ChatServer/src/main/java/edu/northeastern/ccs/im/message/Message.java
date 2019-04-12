package edu.northeastern.ccs.im.message;

import edu.northeastern.ccs.im.MessageType;
import edu.northeastern.ccs.im.model.MessageDAO;
import edu.northeastern.ccs.im.model.MessageModel;
import edu.northeastern.ccs.im.model.User;
import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.ServerConstants;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;
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

  interface JSONLambda {
    Message messageContructor(JSONObject json);
  }

  /**
   * The map of string message type to message class used to dynamically access message constructors based
   * on message types.
   */
  private static HashMap<String, JSONLambda> MESSAGE_TYPE_MAP;


  public static final String BODY = "body";
  public static final String GROUP_ID = "groupId";
  public static final String RECEIVERS = "receivers";
  public static final String USERNAME = "username";
  public static final String PW = "password";
  public static final String USER_ID   = "userId";

  static {
    MESSAGE_TYPE_MAP = new HashMap<>();
    MESSAGE_TYPE_MAP.put(MessageType.PRIVATE.toString(), (json) -> new PrivateMessage(json));
    MESSAGE_TYPE_MAP.put(MessageType.GROUP.toString(), (json) -> new GroupMessage(json));
    MESSAGE_TYPE_MAP.put(MessageType.HELLO.toString(), (json) -> new SimpleLoginMessage(json));
    MESSAGE_TYPE_MAP.put(MessageType.QUIT.toString(), (json) -> new QuitMessage(json));
  }



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
  protected String messageBody;

  /**
   * The sender's username
   */
  protected String senderUsername;

  /**
   * The sender's user object
   */
  protected User user;


  /**
   * Given a handle, name and jsonMsg, return the appropriate message instance or an instance from a
   * subclass of message.
   *
   * @param jsonMsg JSONObject containing the information.
   * @param handle the message type
   * @return Instance of message (or its subclasses) representing the handle, name, & text.
   */
  public static Message makeMessage(String handle, JSONObject jsonMsg) {
    if (MESSAGE_TYPE_MAP.containsKey(handle)) {
      JSONLambda lambda = MESSAGE_TYPE_MAP.get(handle);
      return lambda.messageContructor(jsonMsg);
    }
    return BroadcastMessage.makeBroadcastMessage(ServerConstants.BOUNCER_ID,
            "Invalid Message.");
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
    return this.senderUsername;
  }

  /**
   * Return the body of this message.
   *
   * @return String equal to the text sent by this message.
   */
  public String getBody() {
    return messageBody;
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
   * Appends the message's type to the string message.
   *
   * @param builder the builder that is being used to stringify the message.
   */
  protected void appendMessageType(StringBuilder builder) {
    if (userId != -1) {
      builder.append(" ").append(Integer.toString(userId).length()).append(" ").append(userId);
    } else {
      builder.append(" ").append(NULL_OUTPUT.length()).append(" ").append(NULL_OUTPUT);
    }
  }


  /**
   * Appends the body of the message to the string message.
   *
   * @param builder the builder that is being used to stringify the message
   */
  protected void appendBody(StringBuilder builder) {
    if (this.messageBody != null) {
      builder.append(" ").append(this.messageBody.length()).append(" ").append(this.messageBody);
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
    this.appendBody(result);
    return result.toString();
  }

  public abstract void send(ConcurrentMap<Integer, ClientRunnable> active);

  public boolean loginSucceeds(){ return false; }

  public abstract void persist();

  public void deleteMessage(MessageModel m){
    MessageDAO dao = new MessageDAO(new StringBuilder());
    m.setDeleted(true);
    dao.deleteMessage(m);
  }

}

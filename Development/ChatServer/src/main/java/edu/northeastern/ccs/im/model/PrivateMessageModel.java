package edu.northeastern.ccs.im.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type Private message model.
 */
@Entity
public class PrivateMessageModel extends MessageModel {

  @Column(name = "`body`")
  private String body;

  @Column(name = "`to`")
  private int receiverId;

  /**
   * Get the body of the message.
   *
   * @return the body of the message.
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Set the body of the message.
   *
   * @param body the text of the messge.
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Gets receiver id.
   *
   * @return receiver id
   */
  public int getReceiverId() {
    return this.receiverId;
  }

  /**
   * Sets receiver id.
   *
   * @param receiverId the receiver id
   */
  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }
}

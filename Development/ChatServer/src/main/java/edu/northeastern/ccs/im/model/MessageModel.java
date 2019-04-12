package edu.northeastern.ccs.im.model;

import javax.persistence.*;

/**
 * The type Message model.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "`messages`")
public class MessageModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "`idmessages`")
  private int messageId;

  @Column(name = "`from`")
  private int senderId;

  @Column(name = "`is_deleted`")
  private boolean deleted;

  /**
   * Is deleted boolean.
   *
   * @return the boolean
   */
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * Sets deleted.
   *
   * @param deleted the deleted
   */
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Return the message id.
   *
   * @return the message id.
   */
  public int getMessageId() {
    return this.messageId;
  }

  /**
   * Set the message id
   *
   * @param id the id of the message.
   */
  public void setMessageId(int id) {
    this.messageId = id;
  }

  /**
   * Return the id of the creator of the message.
   *
   * @return the id of the user who created the message.
   */
  public int getSenderId() {
    return this.senderId;
  }

  /**
   * Set the user id.
   *
   * @param id the id of the user who created this message.
   */
  public void setSenderId(int id) {
    this.senderId = id;
  }

}

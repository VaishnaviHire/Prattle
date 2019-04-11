package edu.northeastern.ccs.im.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Message")
public class MessageModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "messageId")
  private int messageId;

  @Column(name = "userId")
  private int userId;

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
  public int getUserId() {
    return this.userId;
  }

  /**
   * Set the user id.
   *
   * @param id the id of the user who created this message.
   */
  public void setUserId(int id) {
    this.userId = id;
  }

}

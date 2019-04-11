package edu.northeastern.ccs.im.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "group message")
public class GroupMessageModel extends MessageModel {

  @Column(name = "body")
  private String body;

  @Column(name = "receiverIds")
  private List<Integer> receiverIds;

  @Column(name = "groupId")
  private int groupId;

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
   * @param body the text
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Get the receivers ids from the message.
   *
   * @return the ids of the receivers of this message.
   */
  public List<Integer> getReceiverIds() {
    return this.receiverIds;
  }

  /**
   * Set the receivers ids in the message.
   *
   * @param receiverIds the ids of receivers of this message.
   */
  public void setReceiverIds(List<Integer> receiverIds) {
    this.receiverIds = receiverIds;
  }

  /**
   * Get the id of the Group of in the message.
   *
   * @return the id of the group in the message.
   */
  public int getGroupId() {
    return this.groupId;
  }

  /**
   * Set the group id in the message.
   *
   * @param groupId Group id to be set in the message.
   */
  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }
}

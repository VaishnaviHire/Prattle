package edu.northeastern.ccs.im.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type Broadcast message model.
 */
@Entity
public class BroadcastMessageModel extends MessageModel {

  @Column(name = "`body`")
  private String body;

  /**
   * Gets body.
   *
   * @return the body
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Sets body.
   *
   * @param body the body
   */
  public void setBody(String body) {
    this.body = body;
  }

}

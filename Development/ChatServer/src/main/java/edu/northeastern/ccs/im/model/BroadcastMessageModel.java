package edu.northeastern.ccs.im.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "broadcast message")
public class BroadcastMessageModel extends MessageModel {

  @Column(name = "body")
  private String body;

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

}

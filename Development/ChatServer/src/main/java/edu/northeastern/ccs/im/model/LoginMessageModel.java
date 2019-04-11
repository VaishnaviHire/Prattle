package edu.northeastern.ccs.im.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Login Message")
public class LoginMessageModel extends MessageModel {

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  /**
   * Return the username of the user trying to login.
   *
   * @return the username.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Set the username.
   *
   * @param username username of the user.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get the password in the login message.
   *
   * @return the password in the message.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Set the password in the message.
   *
   * @param password the password in the message.
   */
  public void setPassword(String password) {
    this.password = password;
  }
}

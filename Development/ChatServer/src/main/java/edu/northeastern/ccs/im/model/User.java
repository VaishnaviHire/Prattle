package edu.northeastern.ccs.im.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * Class defining user model
 */
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "userId")
  private int userId;
  @Column(name = "username")
  private String uName;
  @Column(name = "password")
  private String password;
  @Column(name = "first_name")
  private String first_name;
  @Column(name = "last_name")
  private String last_name;
  @Column(name = "date_of_birth")
  private Date dob;
  @Column(name = "is_private")
  private boolean is_private;

  public User() {

  }

  public User(String username) {
    this.uName = username;
  }

  public User(String username, String password) {
    this.uName = username;
    this.password = password;
  }


  /**
   * @return method to get username
   */

  public String getName() {
    return uName;
  }

  /**
   * @param name method to set given name to user
   */
  public void setName(String name) {
    this.uName = name;

  }

  /**
   * @return the type of unifier
   */
  public boolean isGroup() {
    return false;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUName() {
    return uName;
  }

  public void setUName(String username) {
    this.uName = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public boolean isIs_private() {
    return is_private;
  }

  public void setIs_private(boolean is_private) {
    this.is_private = is_private;
  }
}

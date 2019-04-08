package edu.northeastern.ccs.im.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Class defining user model
 */
@Entity
@Table(name = "user")
public class User{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "userId")
  private int userId;
  @Column(name = "username")
  private String uName;
  @Column(name = "password")
  private String password;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "date_of_birth")
  private Date dob;
  @Column(name = "is_private")
  private boolean isPrivate;
  public User(){

  }
  public User(String username) {
    this.uName = username;
  }
  public User(String username,String password){
  this.uName = username;
  this.password = password;
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

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    this.isPrivate = aPrivate;
  }
}

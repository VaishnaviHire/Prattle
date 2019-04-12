package edu.northeastern.ccs.im.model;

import javax.persistence.*;

import java.util.Date;

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
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "date_of_birth")
  private Date dob;
  @Column(name = "is_private")

  private boolean isPrivate;

  /**
   * Instantiates a new User.
   */
  public User(){

  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   */
  public User(String username) {
    this.uName = username;
  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   * @param password the password
   */
  public User(String username,String password){
  this.uName = username;
  this.password = password;
  }
  /**
   * Sets name.
   *
   * @param name method to set given name to user
   */
  public void setName(String name) {
    this.uName = name;

  }

  /**
   * Is group boolean.
   *
   * @return the type of unifier
   */
  public boolean isGroup() {
    return false;
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Gets u name.
   *
   * @return the u name
   */
  public String getUName() {
    return uName;
  }

  /**
   * Sets u name.
   *
   * @param username the username
   */
  public void setUName(String username) {
    this.uName = username;
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets dob.
   *
   * @return the dob
   */
  public Date getDob() {
    return dob;
  }

  /**
   * Sets dob.
   *
   * @param dob the dob
   */
  public void setDob(Date dob) {
    this.dob = dob;
  }

  /**
   * Is private boolean.
   *
   * @return the boolean
   */
  public boolean isPrivate() {
    return isPrivate;
  }

  /**
   * Sets private.
   *
   * @param aPrivate the a private
   */
  public void setPrivate(boolean aPrivate) {
    this.isPrivate = aPrivate;
  }

}

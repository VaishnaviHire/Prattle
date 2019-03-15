package edu.northeastern.ccs.im.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining user model
 */
public class User implements Unifier {

  private String username;
  private String userRole;
  private List<Unifier> members;

  public User(String username) {
    // Modify the constructor to create user with clientRunnable
    this.username = username;
    this.userRole = "plain_user";
    this.members = new ArrayList<>();
  }

  /**
   * @return the role of user as a plain user or admin
   */
  public String getUserRole() {
    return userRole;
  }

  /**
   * @param role set user to specified role
   */
  public void setUserRole(String role) {
    this.userRole = role;
  }


  /**
   * @return method to get username
   */
  @Override
  public String getName() {
    return username;
  }

  public List<Unifier> getMembers() {
    return this.members;
  }

  /**
   * @param name method to set given name to user
   */
  @Override
  public void setName(String name) {
    this.username = name;

  }

  /**
   * @return the type of unifier
   */
  @Override
  public boolean isGroup() {
    return false;
  }

}

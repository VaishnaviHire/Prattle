package edu.northeastern.ccs.im.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining group in the server
 */
public class Group implements Unifier {

  private String groupName;
  private int groupId;
  protected List<Unifier> members;
  private UserToAdminAdapter admin;

  /**
   * @param groupName name of the group
   * @param admin     User responsible for creation of the group
   */
  public Group(String groupName, User admin) {

    this.groupName = groupName;
    this.admin = UserToAdminAdapter.getInstance(admin, groupName);
    members = new ArrayList<>();
    this.members.add(admin);
  }


  /**
   * Function to get all users in the group : active and inactive
   *
   * @return List of users (groups and user objects)
   */
  @Override
  public List<Unifier> getMembers() {
    return members;
  }

  /**
   * @return the group name
   */
  @Override
  public String getName() {
    return groupName;
  }

  /**
   * @param name name of the group
   */
  @Override
  public void setName(String name) {
    this.groupName = name;

  }

  /**
   * @return the unifier type
   */
  @Override
  public boolean isGroup() {
    return true;
  }

  /**
   * @return the admin object in the group
   */
  public UserToAdminAdapter getAdmin() {
    return admin;
  }


}

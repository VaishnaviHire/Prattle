package edu.northeastern.ccs.im.model;

import java.util.List;

/**
 * Class to adapt user as an admin
 */
public class UserToAdminAdapter implements Admin {
  private User user;
  private static UserToAdminAdapter adminobj;
  private String groupname;


  public static UserToAdminAdapter getInstance(User user, String groupname) {

    if (adminobj == null)
      adminobj = new UserToAdminAdapter(user, groupname);
    return adminobj;
  }


  /**
   * Singleton Pattern , as Admin object is created when creating a group
   */
  private UserToAdminAdapter(User user, String groupname) {
    this.user = user;
    this.user.setUserRole("admin");
    this.groupname = groupname;

  }


  /**
   * @param user the user object which needs to be added to the specified group
   */
  @Override
  public void addUserToGroup(User user, Group group) {
    List<Unifier> memberGroups = user.getMembers();
    List<Unifier> memberUsers = group.getMembers();
    memberGroups.add(group);
    memberUsers.add(user);
  }

  /**
   * @param user user to be deleted
   */
  @Override
  public void deleteUserFromGroup(User user, Group group) {
    List<Unifier> memberGroups = user.getMembers();
    List<Unifier> memberUsers = group.getMembers();
    memberUsers.remove(user);
    memberGroups.remove(group);
  }

  /**
   * @return name of the admin
   */
  @Override
  public String getAdminName() {
    return user.getName();
  }

  public String getGroupName() {
    return groupname;
  }


}

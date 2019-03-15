package edu.northeastern.ccs.im.model;

/**
 * Interface which defines Admin role for the user
 */
public interface Admin {

  void addUserToGroup(User user, Group group);

  void deleteUserFromGroup(User user, Group group);

  String getAdminName();

  String getGroupName();

}

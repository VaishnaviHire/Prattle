package edu.northeastern.ccs.im.model;

/**
 *  Interface which defines Admin role for the user
 */
public interface Admin {

    public void createGroup(String groupname);
    public void addUserToGroup(User user);
    public void deleteUserFromGroup(User user);

}

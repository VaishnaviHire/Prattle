package edu.northeastern.ccs.im.model;

import java.util.List;

/**
 *  Interface which defines Admin role for the user
 */
public interface Admin {

    public void addUserToGroup(User user, List<Unifier> userlist);
    public void deleteUserFromGroup(User user, List<Unifier> userlist);
    public String getAdminName();
    public String getGroupName();

}

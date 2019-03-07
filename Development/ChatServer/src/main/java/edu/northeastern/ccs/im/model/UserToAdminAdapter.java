package edu.northeastern.ccs.im.model;

/**
 * Class to adapt user as an admin
 */
public class UserToAdminAdapter implements Admin {
    private User user;


    UserToAdminAdapter(User user){
        this.user = user;
    }

    /**
     *
     * @param groupname the name of the group to be created
     */
    @Override
    public void createGroup(String groupname) {
        this.user.setUserRole("admin");

    }

    /**
     *
     * @param user the user object which needs to be added to the specified group
     */
    @Override
    public void addUserToGroup(User user) {
        // Implement functionality

    }

    @Override
    public void deleteUserFromGroup(User user) {
        // Implement functionality

    }
}

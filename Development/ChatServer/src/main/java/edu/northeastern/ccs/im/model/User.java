package edu.northeastern.ccs.im.model;

/**
 * Class defining user model
 */
public class User implements Unifier {

    private String username;
    private String userRole;

    User(String username){
        // Modify the constructor to create user with clientRunnable
        this.username = username;
        this.userRole = "plain_user";
    }

    /**
     *
     * @return the role of user as a plain user or admin
     */
    public String  getUserRole(){
        return userRole;
    }

    /**
     *
     * @param role set user to specified role
     */
    public void  setUserRole(String role){
         this.userRole = role;
    }

    /**
     *
     * @return method to get username
     */
    @Override
    public String getName() {
        return username;
    }

    /**
     *
     * @param name method to set given name to user
     */
    @Override
    public void setName(String name) {
        this.username = name;

    }

    /**
     *
     * @return the type of unifier
     */
    @Override
    public String getType() {
        return "user";
    }
}

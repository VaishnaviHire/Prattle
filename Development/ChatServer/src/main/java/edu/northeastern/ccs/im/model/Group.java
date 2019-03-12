package edu.northeastern.ccs.im.model;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class defining group in the server
 */
public class Group implements Unifier {

    private String groupName;
    protected List<Unifier> userlist;
    private UserToAdminAdapter admin;

    /**
     *
     * @param groupName name of the group
     * @param admin User responsible for creation of the group
     */
    public Group(String groupName, User admin){

        this.groupName = groupName;
        this.admin =  UserToAdminAdapter.getInstance(admin,groupName);
        userlist = new ArrayList<>();
        this.userlist.add(admin);
    }



    /**
     *  Function to get all users in the group : active and inactive
     * @return List of users (groups and user objects)
     */
    public List<Unifier> getAllUsers(){

        return userlist;
    }

    /**
     *
     * @return returns only the active users in the group
     */
    public List<Unifier> getActiveUsers(){
        // Modify method with active users only
        return new ArrayList<>();
    }

    /**
     *
     * @return the group name
     */
    @Override
    public String getName() {
        return groupName;
    }

    /**
     *
     * @param name name of the group
     */
    @Override
    public void setName(String name) {
        this.groupName = name;

    }

    /**
     *
     * @return the unifier type
     */
    @Override
    public String getType() {
        return "group";
    }

    /**
     *
     * @return  the admin object in the group
     */
    public UserToAdminAdapter getAdmin(){
        return admin;
    }


}

package edu.northeastern.ccs.im.model;


import java.util.List;

/**
 * The type User dao.
 */
public class UserDAO {
    private StringBuilder output;

    /**
     * Instantiates a new User dao.
     *
     * @param output the output
     */
    public UserDAO(StringBuilder output) {
        this.output = output;
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List getAllUsers() {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        return dataBaseOperation.getAllRecords("user");
    }

    /**
     * Get user user.
     *
     * @param user the user
     * @return the user
     */
    public User getUser(User user){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificUser(user.getUName(),user.getPassword());
        if(l!=null){
            return (User)l;
        }else{
            return null;
        }
    }

    /**
     * Get user non private list.
     *
     * @return the list
     */
    public List getUserNonPrivate(){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        List<Object> l = null;
        l = dataBaseOperation.getAllRecordsNonPrivate("user");
        return l;
    }

}

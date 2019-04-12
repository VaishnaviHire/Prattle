package edu.northeastern.ccs.im.model;


import java.util.List;

public class UserDAO {
    private StringBuilder output;
    public UserDAO(StringBuilder output) {
        this.output = output;
    }

    public User getUser(User user){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificUser(user.getUName(),user.getPassword());
        if(l!=null){
            User u = (User)l;
            return u;
        }else{
            return null;
        }
    }
    public List getUserNonPrivate(){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        List<Object> l = null;
        l = dataBaseOperation.getAllRecordsNonPrivate("user");
        return l;
    }

}

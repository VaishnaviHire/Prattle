package edu.northeastern.ccs.im.model;

import java.util.List;

public class GroupDao {
    private StringBuilder output;
    public GroupDao(StringBuilder output) {
        this.output = output;
    }

    public Group getGroup(Group grp){

        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificGrp(grp.getName());
        if(l!=null){
            Group g = (Group)l;
            return g;
        }else{
            return null;
        }
    }

    public List getGroupNonPrivate(){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        List l = null;
        l = dataBaseOperation.getAllRecordsNonPrivate("group");
        return l;
    }
}

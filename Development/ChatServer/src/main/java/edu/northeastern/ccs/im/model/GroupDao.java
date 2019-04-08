package edu.northeastern.ccs.im.model;

import java.util.List;

public class GroupDao {
    private StringBuilder output;
    public GroupDao(StringBuilder output) {
        this.output = output;
    }
    public List getAllGroups() {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        return dataBaseOperation.getAllRecords("group");
    }

    public Group1 getGroup(Group1 grp){

        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificGrp(grp.getName());
        if(l!=null){
            return (Group1)l;
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

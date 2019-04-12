package edu.northeastern.ccs.im.model;

import java.util.List;

/**
 * The type Group dao.
 */
public class GroupDao {
    private StringBuilder output;

    /**
     * Instantiates a new Group dao.
     *
     * @param output the output
     */
    public GroupDao(StringBuilder output) {
        this.output = output;
    }

    /**
     * Gets all groups.
     *
     * @return the all groups
     */
    public List getAllGroups() {
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        return dataBaseOperation.getAllRecords("group");
    }

    /**
     * Get group group 1.
     *
     * @param grp the grp
     * @return the group 1
     */
    public Group1 getGroup(Group1 grp){

        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        Object l = dataBaseOperation.getSpecificGrp(grp.getName());
        if(l!=null){
            return (Group1)l;
        }else{
            return null;
        }
    }

    /**
     * Get group non private list.
     *
     * @return the list
     */
    public List getGroupNonPrivate(){
        DataBaseOperations dataBaseOperation = new DataBaseOperations(this.output);
        List l = null;
        l = dataBaseOperation.getAllRecordsNonPrivate("group");
        return l;
    }
}

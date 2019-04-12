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

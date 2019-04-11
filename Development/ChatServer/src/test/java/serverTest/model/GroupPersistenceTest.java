package serverTest.model;

import edu.northeastern.ccs.im.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GroupPersistenceTest {
    /**
     * Checks if the GroupDao is returning all groups in the table or not.
     */
    @Test
    public void getAllGroupTest(){
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        List<Object> x = udao.getAllGroups();
        Group1 v = (Group1)x.get(0);
        assertEquals("user3group111",v.getGroupName());
    }

    /**
     * Checks if the GroupDao is returning a specific Group if it exists or not.
     */
    @Test
    public void getSpecificGroupTest(){
        Group1 g = new Group1("name");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group1 x = udao.getGroup(g);
        assertTrue(x==null);
    }
    @Test
    public void getSpecificGroupTest1(){
        Group1 g = new Group1("name1");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group1 x = udao.getGroup(g);
        assertTrue(x==null);
    }
    @Test
    public void getAllNonPrivateGroupTest(){
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        List<Object> x = udao.getGroupNonPrivate();
        assertFalse(x==null);
    }
    @Test
    public void getSpecificGroupTest2(){
        Group1 g = new Group1("SELECT *");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group1 x = udao.getGroup(g);
        assertTrue(x==null);
    }
}


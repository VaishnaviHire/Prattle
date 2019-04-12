package serverTest.model;

import edu.northeastern.ccs.im.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * The type Group persistence test.
 */
public class GroupPersistenceTest {

    /**
     * Get specific group test.
     */
    @Test
    public void getSpecificGroupTest(){
        Group g = new Group("name");

        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
        assertFalse(x==null);
    }

    /**
     * Get specific group test 1.
     */
    @Test
    public void getSpecificGroupTest1(){
        Group g = new Group("name1");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
        assertTrue(x==null);
    }

    /**
     * Get all non private group test.
     */
    @Test
    public void getAllNonPrivateGroupTest(){
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        List<Object> x = udao.getGroupNonPrivate();
        assertFalse(x==null);
    }

    /**
     * checks if there is a group called SELECT * in the table
     */
    @Test
    public void getSpecificGroupTest2(){
        Group g = new Group("SELECT *");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
        assertTrue(x==null);
    }
}


package serverTest.model;

import edu.northeastern.ccs.im.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GroupPersistenceTest {

    @Test
    public void getSpecificGroupTest(){
        Group g = new Group("name");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
        assertFalse(x==null);
    }
    @Test
    public void getSpecificGroupTest1(){
        Group g = new Group("name1");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
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
        Group g = new Group("SELECT *");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group x = udao.getGroup(g);
        assertTrue(x==null);
    }
}


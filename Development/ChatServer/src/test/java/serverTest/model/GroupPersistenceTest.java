package serverTest.model;

import edu.northeastern.ccs.im.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroupPersistenceTest {
    @Test
    public void getAllGroupTest(){
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        List<Object> x = udao.getAllGroups();
        System.out.println(x==null);
        Group1 v = (Group1)x.get(0);
        System.out.println(v.getName());
    }
    @Test
    public void getSpecificGroupTest(){
        Group1 g = new Group1("name");
        StringBuilder b = new StringBuilder();
        GroupDao udao = new GroupDao(b);
        Group1 x = udao.getGroup(g);
        System.out.println(x.getName());
    }
}


package serverTest.model;


import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.model.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    @Test
    public void createUserTest(){
        User plainUser = new User("adminUser");
        Group1 firstgroup = new Group1("firstGroup");
        assertEquals("firstGroup",firstgroup.getName());
        assertNotNull(firstgroup);
        // Add users;
        User newUser = new User("newUser");
        User newUser1 = new User("newUser");
        List<User> x = new ArrayList<>();
        x.add(newUser);
        x.add(newUser1);
        firstgroup.setMembers(x);
        assertEquals(2, firstgroup.getMembers().size());
        x.remove(0);
        firstgroup.setMembers(x);
        assertEquals(1, firstgroup.getMembers().size());
        firstgroup.setGroupName("modifiedName");
        // Change this: A group object should not set groupname . A user object or Admin should set the group name
        assertEquals("modifiedName", firstgroup.getName());
    }
    @Test
    public void groupTest(){
        Group g = new Group();
        g.setName("this is the name");
        assertEquals("this is the name",g.getName());
    }
    @Test
    public void groupTest1(){
        Group1 g = new Group1();
        g.setGroupName("this is the name");
        g.setGroupId(1);
        g.setPrivate(false);
        g.getGroupId();
        g.getGroupName();
        assertNull(g.getModerators());
        g.setModerators(new ArrayList<>());
        assertFalse(g.isPrivate());
        assertEquals("this is the name",g.getName());
    }
//    @Test
//    public void checkGroupMessage(){
//        Message m = ma
//    }
}

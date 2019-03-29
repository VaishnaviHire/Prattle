package serverTest.model;


import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.*;

public class GroupTest {

    @Test
    public void createUserTest(){
        User plainUser = new User("adminUser");
        Group1 firstgroup = new Group1("firstGroup");
        assertEquals("firstGroup",firstgroup.getName());
        assertNotNull(firstgroup);
        // Add users;
        User newUser = new User("newUser");
        assertEquals(2, firstgroup.getMembers().size());
        assertEquals(1, firstgroup.getMembers().size());
        // Change this: A group object should not set groupname . A user object or Admin should set the group name
        assertEquals("modifiedName", firstgroup.getName());


    }
}

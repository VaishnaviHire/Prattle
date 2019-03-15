package serverTest.model;


import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.*;

public class GroupTest {

    @Test
    public void createUserTest(){

        User plainUser = new User("adminUser");

        Group firstgroup = new Group("firstGroup", plainUser);
        UserToAdminAdapter adminUser = firstgroup.getAdmin();

        assertEquals("firstGroup",firstgroup.getName());
        assertTrue(firstgroup.isGroup());
        assertEquals(adminUser,firstgroup.getAdmin());
        assertNotNull(firstgroup);

        // Add users;
        User newUser = new User("newUser");
        adminUser.addUserToGroup(newUser, firstgroup);

        assertEquals(2, firstgroup.getMembers().size());
        adminUser.deleteUserFromGroup(newUser, firstgroup);
        assertEquals(1, firstgroup.getMembers().size());

        // Change this: A group object should not set groupname . A user object or Admin should set the group name

        firstgroup.setName("modifiedName");
        assertEquals("modifiedName", firstgroup.getName());

        assertEquals("firstGroup", adminUser.getGroupName());

    }
}

package serverTest.model;

import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.Calendar;

/**
 * The type User test.
 */
public class UserTest {

    /**
     * User object checking nature of the object.
     */
    @Test
    public void createUserTest(){
        User firstuser = new User("firstUser");
        assertEquals("firstUser",firstuser.getUName());
        assertFalse(firstuser.isGroup());
        firstuser.setName("modifiedName");
        assertEquals("modifiedName", firstuser.getUName());

    }

    /**
     * Test user and see if it functioning as expected.

     */
    @Test
    public void testUser(){
        User u = new User();
        u.setUName("Shivam");
        u.setName("Shivam");
        u.setDob(new Date(Calendar.getInstance().getTime().getTime()));
        u.setPrivate(false);
        u.setFirstName("Shivam");
        u.setLastName("Patel");
        u.setPassword("this");
        u.setUserId(1);
        assertEquals("Shivam",u.getUName());
        assertEquals("this",u.getPassword());
        assertEquals("Shivam",u.getFirstName());
        assertEquals(new Date(Calendar.getInstance().getTime().getTime()).toString(),u.getDob().toString());
        assertEquals("Patel",u.getLastName());
        assertEquals(false,u.isPrivate());
        assertEquals(1,u.getUserId());

    }
}

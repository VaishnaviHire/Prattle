package serverTest.model;

import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.Calendar;

public class UserTest {

    /**
     * User object checking nature of the object.
     */
    @Test
    public void createUserTest(){
        User firstuser = new User("firstUser");
        assertEquals("firstUser",firstuser.getName());
        assertFalse(firstuser.isGroup());
        firstuser.setName("modifiedName");
        assertEquals("modifiedName", firstuser.getName());

    }

    /**
     * User object tests
     */
    @Test
    public void testUser(){
        User u = new User();
        u.setUName("Shivam");
        u.setName("Shivam");
        u.setDob(new Date(Calendar.getInstance().getTime().getTime()));
        u.setIs_private(false);
        u.setFirst_name("Shivam");
        u.setLast_name("Patel");
        u.setPassword("this");
        u.setUserId(1);
        assertEquals("Shivam",u.getUName());
        assertEquals("this",u.getPassword());
        assertEquals("Shivam",u.getName());
        assertEquals(new Date(Calendar.getInstance().getTime().getTime()).toString(),u.getDob().toString());
        assertEquals("Shivam",u.getFirst_name());
        assertEquals("Patel",u.getLast_name());
        assertEquals(false,u.isIs_private());
        assertEquals(1,u.getUserId());
    }
}

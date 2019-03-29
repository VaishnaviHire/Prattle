package serverTest.model;

import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.Calendar;

public class UserTest {

    @Test
    public void createUserTest(){
        User firstuser = new User("firstUser");

        assertEquals("firstUser",firstuser.getName());
        assertFalse(firstuser.isGroup());
        firstuser.setName("modifiedName");
        assertEquals("modifiedName", firstuser.getName());

    }
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
        u.getUName();
        u.getPassword();
        u.getName();
        u.getDob();
        u.getFirst_name();
        u.getLast_name();
        u.isIs_private();
        u.getUserId();

    }
}

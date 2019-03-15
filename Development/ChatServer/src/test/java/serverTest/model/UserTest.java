package serverTest.model;

import edu.northeastern.ccs.im.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;

public class UserTest {

    @Test
    public void createUserTest(){
        User firstuser = new User("firstUser");

        assertEquals("firstUser",firstuser.getName());
        assertFalse(firstuser.isGroup());
        assertEquals("plain_user", firstuser.getUserRole());
        firstuser.setName("modifiedName");

        assertEquals("modifiedName", firstuser.getName());

    }
}

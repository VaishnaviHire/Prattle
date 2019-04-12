package serverTest.model;

import edu.northeastern.ccs.im.model.User;
import edu.northeastern.ccs.im.model.UserDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * The type User persistance test.
 */
public class UserPersistanceTest {
    /**
     * Test get specific user.
     */
    @Test
    public void testGetSpecificUser(){
        StringBuilder b = new StringBuilder();
        User u = new User("user1","$2a$10$l5yWrk9hx0G3elzapp3t0.CWhUNCehQEUpBZ44vwhSUwmf7NueGRa");
        UserDAO udao = new UserDAO(b);
        User u1 = udao.getUser(u);
        if(u1!=null){
            System.out.println(u1.getUName());
        }
    }

    /**
     * Test get non private user.
     */
    @Test
    public void testGetNonPrivateUser(){
        StringBuilder b = new StringBuilder();
        UserDAO udao = new UserDAO(b);
        List<Object> x = udao.getUserNonPrivate();
        System.out.println("1I am printing this shit here to checking if something is printing"+((User)x.get(1)).getUName());
        System.out.println("2I am printing this shit here to checking if something is printing"+((User)x.get(2)).getUName());
        System.out.println("3I am printing this shit here to checking if something is printing"+((User)x.get(3)).getUName());
    }

    /**
     * Test get specific user 2.
     */
    @Test
    public void testGetSpecificUser2(){
        StringBuilder b = new StringBuilder();
        User u = new User("shivam","$2a$10$l5yWrk9hx0G3elzapp3t0.CWhUNCehQEUpBZ44vwhSUwmf7NueGRa");
        UserDAO udao = new UserDAO(b);
        User u1 = udao.getUser(u);
        assertTrue(u1==null);
    }
}

package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team101.RegistrationModule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userRepository")
public interface UserRepo  extends JpaRepository<User, Long> {

    /**
     *
     * @param username display name of the user
     * @return user objects associated with that name
     */
    User findByUsername(String username);

    /**
     *
     * @param userid unique id of user
     * @return  user objects associated with that id
     */
    User findByUserid(int userid);

    /**
     *
     * @param userid unique id of the user
     * @return list of all public usernames
     */
    @Query("SELECT u.username from User u where u.userid <> :userid ")
        List<String> getAllUsername(@Param("userid") int userid);



    @Modifying
    @Query("delete from User u where u.userid <> :userid ")
    void delete(@Param("userid") int userid);


}

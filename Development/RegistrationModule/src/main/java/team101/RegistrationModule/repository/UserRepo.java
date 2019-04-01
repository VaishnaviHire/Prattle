package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team101.RegistrationModule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userRepository")
public interface UserRepo  extends JpaRepository<User, Long> {
        User findByUsername(String username);

        User findByUserid(int userid);

        @Query("SELECT u.username from User u where u.userid <> :userid ")
        List<String> getAllUsername(@Param("userid") int userid);
    }




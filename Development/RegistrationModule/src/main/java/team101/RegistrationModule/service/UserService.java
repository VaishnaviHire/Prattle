package team101.RegistrationModule.service;

import team101.RegistrationModule.model.User;
import team101.RegistrationModule.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;


@Service("userService")
public class UserService {

    private UserRepo userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     *
      * @param username display name of the user
     * @return user object with the given name
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * insert user object into table
     * @param user
     */
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        userRepository.save(user);
    }

    /**
     *
     * @param user
     * @return list of all other users
     */
    public List<String> allUsernames(User user){
        return userRepository.getAllUsername(user.getUserid());
    }



    @Transactional
    public void deleteUser(User user){
        userRepository.delete(user.getUserid());
    }

}
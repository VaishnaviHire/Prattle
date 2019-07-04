package team101.RegistrationModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.Group;
import team101.RegistrationModule.model.GroupAdmin;
import team101.RegistrationModule.repository.GroupAdminRepo;
import team101.RegistrationModule.model.User;

import javax.transaction.Transactional;
import java.util.*;


@Service("groupAdminService")
public class GroupAdminService {

    private GroupAdminRepo groupAdminRepo;

    @Autowired
    public GroupAdminService(GroupAdminRepo groupAdminRepo) {
        this.groupAdminRepo = groupAdminRepo;
    }

    /**
     * Insert given object into database
     * @param groupAdmin
     */
    public void saveGroupAdmin(GroupAdmin groupAdmin){
        groupAdminRepo.save(groupAdmin);
    }

    /**
     *
     * @param u user object
     * @return list of string associate with user as admin
     */
    public List<String> getMyGroups(User u){
        return groupAdminRepo.find(u.getUserid());
    }

    /**
     * Function to determine the list of group names where the given user is neither an admin nor a member
     * @param u  user object
     * @return list of required group names
     */
    public List<String> getPublicGroups(User u){
        return groupAdminRepo.findPublicGroups(u.getUserid());
    }

    public GroupAdmin getGroupAdminByGroup(Group group){
       return groupAdminRepo.findByGroup(group);
    }

    @Transactional
    public void deleteGroupAdminById(User user, Group group){
        groupAdminRepo.deleteGroupAdmin(user.getUserid(),group.getGroupid());
    }

}

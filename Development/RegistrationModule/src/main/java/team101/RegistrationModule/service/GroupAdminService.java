package team101.RegistrationModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.GroupAdmin;
import team101.RegistrationModule.repository.GroupAdminRepo;
import team101.RegistrationModule.model.User;
import java.util.*;


@Service("groupAdminService")
public class GroupAdminService {

    private GroupAdminRepo groupAdminRepo;

    @Autowired
    public GroupAdminService(GroupAdminRepo groupAdminRepo) {
        this.groupAdminRepo = groupAdminRepo;
    }

    public void saveGroupAdmin(GroupAdmin groupAdmin){
//        groupAdmin.setGroup(groupAdmin.getGroup());
//        groupAdmin.setUser(groupAdmin.getUser());
//        groupAdmin.getUser().addGroupAdmin(groupAdmin);
        groupAdminRepo.save(groupAdmin);
    }

    public List<String> getMyGroups(User u){
        return groupAdminRepo.find(u.getUserid());
    }

    public List<String> getPublicGroups(User u){
        return groupAdminRepo.findPublicGroups(u.getUserid());
    }

}

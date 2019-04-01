package team101.RegistrationModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.GroupAdmin;
import team101.RegistrationModule.model.GroupMember;
import team101.RegistrationModule.model.User;
import team101.RegistrationModule.repository.GroupAdminRepo;
import team101.RegistrationModule.repository.GroupMemberRepo;

import java.util.List;

@Service("groupMemberService")
public class GroupMemberService {
    private GroupMemberRepo groupMemberRepo;

    @Autowired
    public GroupMemberService(GroupMemberRepo groupMemberRepo) {
        this.groupMemberRepo = groupMemberRepo;
    }

    public void saveGroupMember(GroupMember groupMember){
        groupMember.getUser().addGroupMembers(groupMember);
        groupMember.setAdminStatus(groupMember.getAdminStatus());
        groupMemberRepo.save(groupMember);
    }

    public List<String> getMemberGroups(User u){
        return groupMemberRepo.findMemberGroups(u.getUserid());
    }

//    public List<String> getMyGroups(User u){
//        return groupAdminRepo.find(u.getUserid());
//    }
//
//    public List<String> getPublicGroups(User u){
//        return groupAdminRepo.findPublicGroups(u.getUserid());
//    }
}

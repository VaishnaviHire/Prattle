package team101.RegistrationModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.Group;
import team101.RegistrationModule.model.GroupMember;
import team101.RegistrationModule.model.User;
import team101.RegistrationModule.repository.GroupMemberRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service("groupMemberService")
public class GroupMemberService {
    private GroupMemberRepo groupMemberRepo;

    @Autowired
    public GroupMemberService(GroupMemberRepo groupMemberRepo) {
        this.groupMemberRepo = groupMemberRepo;
    }

    /**
     * Insert groupMember object into database
     * @param groupMember
     */
    public void saveGroupMember(GroupMember groupMember){
        groupMember.getUser().addGroupMembers(groupMember);
        groupMember.setAdminStatus(groupMember.getAdminStatus());
        groupMemberRepo.save(groupMember);
    }

    /**
     *
     * @param u user object
     * @return list of group names where the given user is a member
     */
    public List<String> getMemberGroups(User u){
        return groupMemberRepo.findMemberGroups(u.getUserid());
    }

    @Transactional
    public void deleteMembersByUser(User user, Group group){
        groupMemberRepo.deleteMember(user.getUserid(), group.getGroupid());
    }

}

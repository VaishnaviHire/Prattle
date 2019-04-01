package team101.RegistrationModule.service;
import team101.RegistrationModule.model.Group;
import team101.RegistrationModule.model.User;
import team101.RegistrationModule.repository.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@Service("groupService")
public class GroupService {

    private GroupRepo groupRepository;

    @Autowired
    public GroupService(GroupRepo groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group findGroupByGroupName(String groupname) {
        return groupRepository.findByGroupName(groupname);
    }

    public void saveGroup(Group grp) {
        groupRepository.save(grp);
    }


}
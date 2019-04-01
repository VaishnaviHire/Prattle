package team101.RegistrationModule.service;

import team101.RegistrationModule.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.JoinRequests;
import team101.RegistrationModule.model.User;
import team101.RegistrationModule.repository.InvitesRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("invitesService")
public class InvitesService {

    private InvitesRepo invitesRepo;

    @Autowired
    public InvitesService(InvitesRepo invitesRepo) {
        this.invitesRepo = invitesRepo;
    }

//    public Group findGroupByGroupName(String groupname) {
//        return groupRepository.findByGroupName(groupname);
//    }

    public void saveInvite(Invites invite) {
        invitesRepo.save(invite);
    }

    public List<String> getInvites(User u){

        List<Invites> jr =  invitesRepo.findInvites(u.getUserid());

        List<String> result = new ArrayList<>();
        for(Invites request : jr){
            result.add(request.toString());

        }
        return result;
    }

    @Transactional
    public void removeInvite(Group group, User receiver){
        invitesRepo.removeByInviteid(group.getGroupid(), receiver.getUserid());
    }


}
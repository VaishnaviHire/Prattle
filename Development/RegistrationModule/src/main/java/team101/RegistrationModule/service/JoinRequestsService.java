package team101.RegistrationModule.service;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team101.RegistrationModule.model.User;
import team101.RegistrationModule.model.JoinRequests;

import java.util.ArrayList;
import java.util.List;
import team101.RegistrationModule.repository.JoinRequestsRepo;

import javax.transaction.Transactional;

@Service("joinRequestsService")
public class JoinRequestsService {
    private JoinRequestsRepo joinRequestsRepo;

    @Autowired
    public JoinRequestsService(JoinRequestsRepo joinRequestsRepo) {
        this.joinRequestsRepo = joinRequestsRepo;
    }

    /**
     * insert join request into table
     * @param joinRequests
     */
    public void saveJoinRequests(JoinRequests joinRequests){

        joinRequestsRepo.save(joinRequests);
    }

    /**
     *
     * @param u user object
     * @return list of join requests associated with the user
     */
    public List<String> getJoinRequests(User u){

        List<JoinRequests> jr =  joinRequestsRepo.findjoinRequests(u.getUserid());

        List<String> result = new ArrayList<>();
        for(JoinRequests request : jr){
            result.add(request.toString());

        }
        return result;
    }

    /**
     *
     * @param id unique id of request
     * @return request associated with the id
     */
    public JoinRequests findRequestById(int id){
        return joinRequestsRepo.findByRequestid(id);
    }

    /**
     * Function to delete requests given a request id
     * @param id unique id of request
     */
    @Transactional
    public void removeRequest(int id){
        joinRequestsRepo.removeByRequestid(id);
    }
}

package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team101.RegistrationModule.model.JoinRequests;

import java.util.List;

@Repository("joinRequestsRepository")
public interface JoinRequestsRepo extends JpaRepository<JoinRequests, Long> {

    /**
     *
     * @param userid unique id of the user
     * @return list of join requests for the given user
     */
    @Query("SELECT jr FROM JoinRequests jr JOIN GroupAdmin ga ON ga.group.groupid = jr.group.groupid WHERE ga.user.userid = (:userid)")
    public List<JoinRequests> findjoinRequests(@Param("userid") int userid);

    /**
     *
     * @param idjoin_group unique id of the request
     * @return join request object associated with the given id
     */
    JoinRequests findByRequestid(int idjoin_group);

    /**
     * Delete the join reuests which are accepted
     * @param id unique id of the join request
     */
    @Modifying
    @Query("DELETE FROM JoinRequests jr where jr.requestid = (:id)")
    public void removeByRequestid(@Param("id") int id);

}

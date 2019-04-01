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

    @Query("SELECT jr FROM JoinRequests jr JOIN GroupAdmin ga ON ga.group.groupid = jr.group.groupid WHERE ga.user.userid = (:userid)")
    public List<JoinRequests> findjoinRequests(@Param("userid") int userid);

    JoinRequests findByRequestid(int idjoin_group);

    @Modifying
    @Query("DELETE FROM JoinRequests jr where jr.requestid = (:id)")
    public void removeByRequestid(@Param("id") int id);

}

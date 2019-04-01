package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team101.RegistrationModule.model.Invites;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("invitesRepository")
public interface InvitesRepo  extends JpaRepository<Invites, Long> {

    @Query("SELECT iv FROM Invites iv  WHERE iv.receiver.userid = (:userid)")
    public List<Invites> findInvites(@Param("userid") int userid);

    @Modifying
    @Query("DELETE FROM Invites jr where jr.receiver.userid = (:receiverid) AND jr.group.groupid = (:groupid) ")
    public void removeByInviteid(@Param("groupid") int groupid, @Param("receiverid") int receiverid);
}

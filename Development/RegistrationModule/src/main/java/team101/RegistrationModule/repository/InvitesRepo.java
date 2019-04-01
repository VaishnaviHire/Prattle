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

    /**
     *
     * @param userid unique id of the user
     * @return list of invites for a given user
     */
    @Query("SELECT iv FROM Invites iv  WHERE iv.receiver.userid = (:userid)")
    public List<Invites> findInvites(@Param("userid") int userid);

    /**
     * Function to delete the invites when the request is accepted by the admin
     * @param groupid unique id of the group
     * @param receiverid unique id of the user
     */
    @Modifying
    @Query("DELETE FROM Invites jr where jr.receiver.userid = (:receiverid) AND jr.group.groupid = (:groupid) ")
    public void removeByInviteid(@Param("groupid") int groupid, @Param("receiverid") int receiverid);
}

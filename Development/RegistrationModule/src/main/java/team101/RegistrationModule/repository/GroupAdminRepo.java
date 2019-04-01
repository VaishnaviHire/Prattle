package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import team101.RegistrationModule.model.GroupAdmin;

@Repository("groupAdminRepository")
public interface GroupAdminRepo extends JpaRepository<GroupAdmin, Long> {

    /**
     * Function to determine the list of group names associated with a user
     * @param userid unique id of the user
     * @return list of group names where the given user is an admin
     */
    @Query("SELECT g.groupName FROM Group g JOIN GroupAdmin ga ON g.groupid = ga.group.groupid WHERE ga.user.userid = (:userid)")
    public List<String> find(@Param("userid") int userid);

    /**
     * Function to determine the list of group names where the given user is neither an admin nor a member
     * @param userid unique id of the user
     * @return list of required group names
     */
    @Query("SELECT g.groupName FROM Group g , GroupAdmin ga, GroupMember gm  WHERE g.groupid = ga.group.groupid  AND g.groupid = gm.group.groupid AND ga.user.userid <> (:userid) AND g.privateGroup = 0 AND gm.user.userid <> (:userid) ")
    public List<String> findPublicGroups(@Param("userid") int userid);



}

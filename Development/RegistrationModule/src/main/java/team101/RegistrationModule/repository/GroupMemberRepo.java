package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team101.RegistrationModule.model.GroupMember;
import team101.RegistrationModule.model.User;

import java.util.List;

@Repository("groupMemberRepository")
public interface GroupMemberRepo extends JpaRepository<GroupMember, Long> {

    /**
     *  Function to determine the list of group names associated with a user as a member
     * @param userid unique id of the user
     * @return list of group names where the given user is a member
     */
    @Query("SELECT g.groupName FROM Group g JOIN GroupMember gm ON g.groupid = gm.group.groupid WHERE gm.user.userid = (:userid) AND gm.adminStatus = 0")
     List<String> findMemberGroups(@Param("userid") int userid);

    @Modifying
    @Query("DELETE from GroupMember gm WHERE gm.user.userid = (:userid) and gm.group.groupid = (:groupid)")
     void deleteMember(@Param("userid") int userid, @Param("groupid") int groupid);

    @Query("SELECT gm FROM GroupMember gm where gm.user.userid = (:userid) and gm.group.groupid = (:groupid)")
    GroupMember findGroupMemberByUserandAndGroup(@Param("userid") int userid, @Param("groupid") int groupid);

    @Modifying
    @Query("Update GroupMember gm set gm.adminStatus=(:status) where gm.idgroup_member = (:id)")
    void updateMember(@Param("id") int id, @Param("status") int status);

}

package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team101.RegistrationModule.model.Group;

import java.util.List;


@Repository("groupRepository")
    public interface GroupRepo  extends JpaRepository<Group, Long> {
    /**
     * Given a groupname returns group objects associated with the group names
     * @param groupname name of the group
     * @return group object
     */
    Group findByGroupName(String groupname);

    @Modifying
    @Query("DELETE FROM Group g  WHERE g.groupid = (:groupid)")
    public void deleteGroup(@Param("groupid") int groupid);
}

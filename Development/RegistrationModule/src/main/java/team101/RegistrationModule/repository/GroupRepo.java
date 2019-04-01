package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team101.RegistrationModule.model.Group;



@Repository("groupRepository")
    public interface GroupRepo  extends JpaRepository<Group, Long> {
    /**
     * Given a groupname returns group objects associated with the group names
     * @param groupname name of the group
     * @return group object
     */
    Group findByGroupName(String groupname);
}

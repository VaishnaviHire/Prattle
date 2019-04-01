package team101.RegistrationModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team101.RegistrationModule.model.Group;



@Repository("groupRepository")
    public interface GroupRepo  extends JpaRepository<Group, Long> {
    Group findByGroupName(String groupname);
}

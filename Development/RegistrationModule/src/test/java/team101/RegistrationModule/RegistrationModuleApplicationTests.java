package team101.RegistrationModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import team101.RegistrationModule.service.GroupAdminService;
import team101.RegistrationModule.service.GroupService;
import team101.RegistrationModule.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationModuleApplicationTests {
	@Autowired
	GroupAdminService gms;

	@Autowired
	UserService us;

	@Autowired
	GroupService gs;


	@Test
	public void contextLoads() {
//
//		GroupAdmin gm = new GroupAdmin();
//		User user = new User("user3", "ymmot", "user7890","last7890","1995-04-02");
//		Group group = new Group("user3grp001");
//
//		gs.saveGroup(group);
//		GroupAdmin userGroup = new GroupAdmin();
//		user.addGroupMembers(gm);
//
//		us.saveUser(user);



//		gms.saveGroupMember(gm,user,group);


//		gms.saveGroupMember(userGroup,user,group);

//		userGroup.setActivated(true);
//		userGroup.setRegisteredDate(new Date());

//		session.save(userGroup);




	}

}

package team101.RegistrationModule.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import team101.RegistrationModule.model.*;
import team101.RegistrationModule.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupAdminService groupAdminService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private JoinRequestsService joinRequestsService;

    @Autowired
    private InvitesService invitesService;

    /**
     * display create group page
     * @return
     */
    @RequestMapping(value="/admin/home/groups", method = RequestMethod.GET)
    public ModelAndView groups(){
        ModelAndView modelAndView = new ModelAndView();
        Group grp = new Group();
        modelAndView.addObject("group", grp);
        modelAndView.setViewName("group");
        return modelAndView;
    }

    /**
     * endpoint to create a new group
     * @param grp
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/admin/home/groups", method = RequestMethod.POST)
    public ModelAndView createNewGroup(@RequestParam("groupName") String groupname,@RequestParam(value = "checkboxName", required = false) boolean checkboxValue) {
        ModelAndView modelAndView = new ModelAndView();
        int privateValue = (checkboxValue) ? 1: 0;
        Group grp = new Group(groupname,privateValue);

        Group grpExists = groupService.findGroupByGroupName(grp.getGroupName());


        if (grpExists != null) {
            modelAndView.addObject("successMessage", "Group with this name already exists");
            modelAndView.setViewName("group");

        }
        else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(auth.getName());

            GroupAdmin groupAdmin = new GroupAdmin(user,grp);
            GroupMember groupMember = new GroupMember(user,grp,1);
            groupAdminService.saveGroupAdmin(groupAdmin);
            groupMemberService.saveGroupMember(groupMember);
            modelAndView.addObject("successMessage", "Group has been registered successfully");
            modelAndView.addObject("group", new Group());
            modelAndView.setViewName("group");



        }
        return modelAndView;
    }

    /**
     * display join requests
     * @return
     */
    @RequestMapping(value="/admin/home/requests", method = RequestMethod.GET)
    public ModelAndView requests(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("joinRequests",joinRequestsService.getJoinRequests(user));
        modelAndView.setViewName("requests");
        return modelAndView;
    }


    /**
     * endpoint to accept given join requests
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/home/requests", method = RequestMethod.POST)
    public ModelAndView acceptRequests(@RequestParam("requestID") int id){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        JoinRequests joinRequests = joinRequestsService.findRequestById(id);
        GroupMember gm = new GroupMember(joinRequests.getSender(),joinRequests.getGroup(),0);
        groupMemberService.saveGroupMember(gm);
        if(joinRequests.getIsinvite() == 1){
            invitesService.removeInvite(joinRequests.getGroup(),joinRequests.getSender());
        }

        joinRequestsService.removeRequest(id);
        modelAndView.addObject("successMessage", "Request successful");
        modelAndView.setViewName("requests");
        return modelAndView;

    }

    @RequestMapping(value="/admin/home/deletegroup", method = RequestMethod.POST)
    public ModelAndView deleteGroupByName(@RequestParam("groupName") String groupname){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        Group group = groupService.findGroupByGroupName(groupname);
        GroupAdmin groupAdmin = groupAdminService.getGroupAdminByGroup(group);
        if(groupAdmin.getUser().getUserid() == user.getUserid()){
            groupService.deleteGroupById(group);
            modelAndView.addObject("successMessage", "Request successful");
            modelAndView.setViewName("deletegroup");
            return modelAndView;
        }
        else{
            modelAndView.addObject("successMessage", "Cannot Delete the group. You need to be admin to delete a group");
            modelAndView.setViewName("deletegroup");
            return modelAndView;

        }

    }

    @RequestMapping(value="/admin/home/deletegroup", method = RequestMethod.GET)
    public ModelAndView dgroups(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deletegroup");
        return modelAndView;
    }

    @RequestMapping(value="/admin/home/profile", method = RequestMethod.GET)
    public ModelAndView myprofile(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("firstname",user.getFirstName());
        modelAndView.addObject("lastname",user.getLastName());
        modelAndView.addObject("dob",user.getDateOfBirth());
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @RequestMapping(value="/admin/home/profile", method = RequestMethod.POST)
    public ModelAndView deleteUser(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("firstname",user.getFirstName());
        modelAndView.addObject("lastname",user.getLastName());
        modelAndView.addObject("dob",user.getDateOfBirth());
        modelAndView.addObject("successMessage", "user deleted");
        userService.deleteUser(user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/admin/home/removemember", method = RequestMethod.POST)
    public ModelAndView removeFromGroup(@RequestParam("groupname") String groupname, @RequestParam("username") String username){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Group group = groupService.findGroupByGroupName(groupname);
        GroupAdmin gadmin = groupAdminService.getGroupAdminByGroup(group);
        User user = userService.findUserByUsername(username);
        User currentUser = userService.findUserByUsername(auth.getName());

        if(((username.equals(currentUser.getUsername())) &&
                (!gadmin.getUser().getUsername().equals(username) )) ||
                ((gadmin.getUser().getUsername().equals(currentUser.getUsername())) &&
                        (!username.equals(currentUser.getUsername()))))
        {
            groupMemberService.deleteMembersByUser(user, group);

            modelAndView.addObject("successMessage", "Removed from group");
            modelAndView.setViewName("removemember");
            return modelAndView;
        }
        else{
            modelAndView.addObject("successMessage", "Remove not allowed");
            modelAndView.setViewName("removemember");
            return modelAndView;
        }

    }

    @RequestMapping(value="/admin/home/removemember", method = RequestMethod.GET)
    public ModelAndView removeMemberDisplay(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("removemember");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/home/assignadmin", method = RequestMethod.POST)
    public ModelAndView assignadmin(@RequestParam("groupname") String groupname, @RequestParam("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
        Group group = groupService.findGroupByGroupName(groupname);
        GroupAdmin groupAdmin = groupAdminService.getGroupAdminByGroup(group);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        User otheruser = userService.findUserByUsername(username);


        if(user.getUsername().equals(groupAdmin.getUser().getUsername()) && ((joinRequestsService.getJoinRequests(user)).isEmpty())){
            groupAdminService.deleteGroupAdminById(user,group);
            groupAdminService.saveGroupAdmin(new GroupAdmin(otheruser,group));

            GroupMember oldadmin = groupMemberService.findGroupMember(user,group);
            GroupMember newadmin = groupMemberService.findGroupMember(otheruser,group);

            groupMemberService.updateMemberStatus(oldadmin,0);
            groupMemberService.updateMemberStatus(newadmin,1);
            modelAndView.addObject("successMessage", " assigned admin for this group");
            modelAndView.setViewName("assignadmin");
            return modelAndView;


        }
        else{
            modelAndView.addObject("successMessage", "Cannot assign admin for this group");
            modelAndView.setViewName("assignadmin");
            return modelAndView;
        }

    }

    @RequestMapping(value="/admin/home/asignadmin", method = RequestMethod.GET)
    public ModelAndView assignAdminDisplay(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("assignadmin");
        return modelAndView;
    }




}

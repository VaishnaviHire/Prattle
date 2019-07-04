package team101.RegistrationModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import team101.RegistrationModule.model.*;
import team101.RegistrationModule.service.*;

@Controller
public class InvitesController {

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
     * display group invites
     * @return
     */
    @RequestMapping(value="/admin/home/sendinvites", method = RequestMethod.GET)
    public ModelAndView sendinvites(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("invites", invitesService.getInvites(user));
        modelAndView.setViewName("sendinvites");
        return modelAndView;
    }

    /**
     * endpoint to create new invite
     * @param groupname
     * @param receivername
     * @return
     */
    @RequestMapping(value="/admin/home/sendinvites", method = RequestMethod.POST)
    public ModelAndView createNewInvites(@RequestParam("groupname") String groupname, @RequestParam("receivername") String receivername){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userService.findUserByUsername(auth.getName());
        User receiver = userService.findUserByUsername(receivername);
        Group group = groupService.findGroupByGroupName(groupname);
        Invites inv = new Invites(sender,group,receiver);
        invitesService.saveInvite(inv);
        JoinRequests jr = new JoinRequests(receiver,group,1);
        joinRequestsService.saveJoinRequests(jr);
        modelAndView.addObject("successMessage", "Request successful");
        modelAndView.setViewName("sendinvites");
        return modelAndView;
    }

    /**
     * Display search form
     * @return
     */
    @RequestMapping(value="/admin/home/search", method = RequestMethod.GET)
    public ModelAndView searchDisplay(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search");
        return modelAndView;
    }

    /**
     * Search given user or group by name
     * @param name
     * @return
     */
    @RequestMapping(value="/admin/home/search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam("name") String name){
        ModelAndView modelAndView = new ModelAndView();
        if(userService.findUserByUsername(name) != null) {
            User user = userService.findUserByUsername(name);
            modelAndView.addObject("username", user.getUsername());
            modelAndView.addObject("firstname",user.getFirstName());
            modelAndView.addObject("lastname",user.getLastName());
            modelAndView.addObject("dob",user.getDateOfBirth());
            modelAndView.addObject("successMessage", "Request successful");
            modelAndView.setViewName("userinfo");
            return modelAndView;
        }
        else if(groupService.findGroupByGroupName(name) != null){
            Group group = groupService.findGroupByGroupName(name);
            GroupAdmin ga = groupAdminService.getGroupAdminByGroup(group);
            modelAndView.addObject("groupname", group.getGroupName());
            modelAndView.addObject("groupAdmin",ga.getUser().getUsername());
            modelAndView.addObject("successMessage", "Request successful");
            modelAndView.setViewName("groupinfo");
            return modelAndView;
        }
        else{
            modelAndView.addObject("successMessage", "No such entity");
            modelAndView.setViewName("search");

            return modelAndView;
        }
    }
}

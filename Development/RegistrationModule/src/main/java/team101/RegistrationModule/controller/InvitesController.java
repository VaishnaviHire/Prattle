package team101.RegistrationModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import team101.RegistrationModule.model.Group;
import team101.RegistrationModule.model.Invites;
import team101.RegistrationModule.model.JoinRequests;
import team101.RegistrationModule.model.User;
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
}

package team101.RegistrationModule.controller;

import org.springframework.web.bind.annotation.*;
import team101.RegistrationModule.model.*;
import team101.RegistrationModule.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupAdminService groupAdminService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JoinRequestsService joinRequestsService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getUsername() + ")");
        modelAndView.addObject("myGroups", groupAdminService.getMyGroups(user));
        modelAndView.addObject("publicGroups", groupAdminService.getPublicGroups(user));
        modelAndView.addObject("memberGroups", groupMemberService.getMemberGroups(user));
        modelAndView.addObject("myFriends", userService.allUsernames(user));

        modelAndView.addObject("group", new Group());
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }




    @RequestMapping(value="/admin/home", method = RequestMethod.POST)
    public ModelAndView createNewRequest(@RequestParam("groupname") String jr) {
            ModelAndView modelAndView = new ModelAndView();
            Group gr = groupService.findGroupByGroupName(jr);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(auth.getName());
                JoinRequests jreq = new JoinRequests(user,gr,0);
                joinRequestsService.saveJoinRequests(jreq);
                modelAndView.addObject("successMessage", "Request successful");
                modelAndView.setViewName("requests");


        modelAndView.setViewName("admin/home");
        return modelAndView;
    }



}

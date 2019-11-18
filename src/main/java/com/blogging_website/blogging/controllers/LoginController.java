package com.blogging_website.blogging.controllers;

import com.blogging_website.blogging.config.ForgetPasswordConfig;
import com.blogging_website.blogging.domain.User;
import com.blogging_website.blogging.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    private Principal principal;

    @GetMapping(path = "/loginUser",produces = "application/json")
    public String login(Principal principal){
        System.out.println("Logging in user.."+principal.getName());
        this.principal=principal;
        return "\"login successfull\"";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logout servlet : "+authentication);

        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            request.getSession().invalidate();

            System.out.println("Logout successful");
            return "\"logout successful\"";
        }

        System.out.println("Logout Unsuccessful");
        return "\"logout unsuccessful\"";
    }

    @GetMapping(path = "/getProfile",produces = "application/json")
    public Optional<User> getProfile(Principal principal){
        System.out.println("Getting Profile of "+principal.getName());
        return userService.getProfile(principal.getName());
    }

    @GetMapping(path = "/getProfile/{id}",produces = "application/json")
    public Optional<User> getProfileById(@PathVariable Long id){
        System.out.println("Getting profile by id");
        return userService.getProfileById(id);
    }

    @PostMapping(path = "/updateProfile", produces = "application/json")
    public String editUser(Principal principal,@RequestBody User user){
        System.out.println("Updating profile.");
        userService.editProfile(principal,user);

        return "\"Editing successful\"";
    }

    @PostMapping(path = "/forgetPassword", produces = "application/json")
    public boolean forgetPassword(@RequestBody String email){
        JSONObject jsonObject=new JSONObject(email);

        System.out.println("Sending mail : "+jsonObject.getString("email"));
        try{
            userService.setNewPassword(jsonObject.getString("email"));
            ForgetPasswordConfig.sendMail(jsonObject.getString("email"),
                    "Skyline - Forget Password Service",
                    "You messed up ? We still got you covered <br> Password : Skyline123@ <br> " +
                            "<b>PLEASE DO NOT SHARE THIS WITH ANYONE</b>");
            return true;
        }
        catch (Exception e){
            System.err.println(e);
            return false;
        }
    }
}

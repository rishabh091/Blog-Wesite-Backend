package com.blogging_website.blogging.controllers;

import com.blogging_website.blogging.domain.User;
import com.blogging_website.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/sendingData", consumes = "application/json")
    public boolean signUp(@RequestBody User user){
        user.setIsActive(1);

        System.out.println("Signup controller is working.");
        return userService.signup(user);
    }

    @PostMapping(path = "/sendOTP",produces = "application/json")
    public int sendOTP(@RequestBody User user){
        System.out.println("Sending OTP..");
        try{
            return userService.sendOtp(user.getEmail());
        }
        catch (Exception e){
            System.err.println(e);
            return 0;
        }
    }

    @GetMapping(path = "/getUsers", produces = "application/json")
    public ArrayList<User> getUsers(Principal principal){
        System.out.println("Getting users to follow..");
        return userService.getUsers(principal);
    }
}

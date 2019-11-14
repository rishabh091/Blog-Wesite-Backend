package com.blogging_website.blogging.controllers;

import com.blogging_website.blogging.domain.User;
import com.blogging_website.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

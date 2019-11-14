package com.blogging_website.blogging.controllers;

import com.blogging_website.blogging.domain.Followers;
import com.blogging_website.blogging.service.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/follow")
public class FollowersController {

    @Autowired
    FollowersService followersService;

    @GetMapping(path = "/sendRequest/{userWhomRequestIsSent}", produces = "application/json")
    public boolean sendRequest(@PathVariable Long userWhomRequestIsSent, Principal principal){
        System.out.println("Sending follow request to : "+userWhomRequestIsSent);
        return followersService.sendRequest(userWhomRequestIsSent,principal);
    }

    @GetMapping(path = "/getAllRequest", produces = "application/json")
    public ArrayList<Followers> getAllRequests(Principal principal){
        System.out.println("Getting all requests of current user : "+principal.getName());
        return followersService.getAllRequests(principal);
    }

    @GetMapping(path = "/acceptRequest/{userWhoSentRequest}", produces = "application/json")
    public boolean acceptRequest(@PathVariable Long userWhoSentRequest,Principal principal){
        System.out.println("Accepting request");
        return followersService.acceptRequest(userWhoSentRequest,principal);
    }

    @GetMapping(path = "/declineRequest/{userWhoSentRequest}" ,produces = "application/json")
    public boolean declineRequest(@PathVariable Long userWhoSentRequest,Principal principal){
        System.out.println("Declining request");
        return followersService.declineRequest(userWhoSentRequest,principal);
    }

    @GetMapping(path = "/getFollowers/{id}", produces = "application/json")
    public ArrayList<Followers> getFollowers(@PathVariable Long id,Principal principal){
        System.out.println("Fetching followers of "+principal.getName());
        return followersService.getFollowers(principal,id);
    }

    @GetMapping(path = "/getFollowing/{id}", produces = "application/json")
    public ArrayList<Followers> getFollowing(@PathVariable Long id,Principal principal){
        System.out.println("Getting Following of "+principal.getName());
        return followersService.getFollowing(principal,id);
    }

    @GetMapping(path = "/unFollow/{id}", produces = "application/json")
    public boolean unFollow(@PathVariable Long id,Principal principal){
        System.out.println("UnFollowing : "+id);
        return followersService.unFollow(principal,id);
    }
}

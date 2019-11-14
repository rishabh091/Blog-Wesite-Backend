package com.blogging_website.blogging.service;

import com.blogging_website.blogging.dao.UserRepository;
import com.blogging_website.blogging.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean signup(User user){
        if(checkUser(user)){
            userRepository.save(user);

            return true;
        }
        else{
            return false;
        }
    }
    private boolean checkUser(User user){
        if(checkUserName(user)){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean checkUserName(User user){
        ArrayList<User> users=(ArrayList<User>) userRepository.findAll();

        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserName().equals(user.getUserName()) || users.get(i).getEmail().equals(user.getEmail())){
                return false;
            }
        }
        return true;
    }
    public Optional<User> getProfile(String email){
        Optional<User> userOptional=userRepository.findByEmail(email);
        return userOptional;
    }
    public void editProfile(Principal principal,User user){
        Optional<User> oldUser=userRepository.findByEmail(principal.getName());
        user.setId(oldUser.get().getId());
        user.setIsActive(1);
        userRepository.save(user);
    }
    public Optional<User> getProfileById(Long id){
        return userRepository.findById(id);
    }

    public void setNewPassword(String email){
        Optional<User> user=userRepository.findByEmail(email);
        System.out.println("Id of user : "+user.get().getId());
        user.get().setPassword("Skyline123@");

        userRepository.save(user.get());
    }
}

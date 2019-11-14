package com.blogging_website.blogging.service;

import com.blogging_website.blogging.dao.FollowersRepository;
import com.blogging_website.blogging.dao.UserRepository;
import com.blogging_website.blogging.domain.Followers;
import com.blogging_website.blogging.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class FollowersService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowersRepository followersRepository;

    public boolean sendRequest(Long id, Principal principal){
        Optional<User> userWhomRequestIsSent=userRepository.findById(id);
        Optional<User> userWhoSentTheRequest=userRepository.findByEmail(principal.getName());
        ArrayList<Followers> followers=(ArrayList<Followers>) followersRepository
                .findAllByUserWhoReceiveRequest(userWhomRequestIsSent);

        for(int i=0;i<followers.size();i++){
            if(followers.get(i).getUserWhoReceiveRequest().getUserName().toLowerCase()
                    .equals(userWhomRequestIsSent.get().getUserName().toLowerCase())
            && followers.get(i).getUserWhoSentRequest().getUserName().toLowerCase()
                    .equals(userWhoSentTheRequest.get().getUserName().toLowerCase())){
                return false;
            }
        }

        Followers followersObj=new Followers();
        followersObj.setRequestAccepted(false);
        followersObj.setUserWhoReceiveRequest(userWhomRequestIsSent.get());
        followersObj.setUserWhoSentRequest(userWhoSentTheRequest.get());

        followersRepository.save(followersObj);
        return true;
    }

    public ArrayList<Followers> getAllRequests(Principal principal){
        Optional<User> user=userRepository.findByEmail(principal.getName());
        return followersRepository.findAllByUserWhoReceiveRequestAndRequestAccepted(user,false);
    }

    public boolean acceptRequest(Long userWhoSentRequestId,Principal principal){
        Optional<User> currentUser=userRepository.findByEmail(principal.getName());
        Optional<User> userWhoSentRequest=userRepository.findById(userWhoSentRequestId);

        try{
            Optional<Followers> followers=followersRepository
                    .findByUserWhoReceiveRequestAndUserWhoSentRequest(currentUser,userWhoSentRequest);
            followers.get().setRequestAccepted(true);

            followersRepository.save(followers.get());
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    @Transactional
    public boolean declineRequest(Long userWhoSentRequestId,Principal principal){
        Optional<User> currentUser=userRepository.findByEmail(principal.getName());
        Optional<User> userWhoSentRequest=userRepository.findById(userWhoSentRequestId);

        try{
            followersRepository.deleteByUserWhoReceiveRequestAndUserWhoSentRequest(currentUser,userWhoSentRequest);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Followers> getFollowers(Principal principal,Long id){
        Optional<User> currentUser=userRepository.findById(id);
        return followersRepository.findAllByUserWhoReceiveRequestAndRequestAccepted(currentUser,true);
    }

    public ArrayList<Followers> getFollowing(Principal principal,Long id){
        Optional<User> currentUser=userRepository.findById(id);
        return followersRepository.findAllByUserWhoSentRequestAndRequestAccepted(currentUser,true);
    }

    @Transactional
    public boolean unFollow(Principal principal,Long id){
        Optional<User> currentUser=userRepository.findByEmail(principal.getName());
        Optional<User> userToUnFollow=userRepository.findById(id);

        try{
            followersRepository.deleteByUserWhoReceiveRequestAndUserWhoSentRequest(userToUnFollow,currentUser);
            return true;
        }
        catch (Exception e){
            System.err.println(e);
            return false;
        }
    }
}

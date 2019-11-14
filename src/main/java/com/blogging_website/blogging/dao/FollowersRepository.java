package com.blogging_website.blogging.dao;

import com.blogging_website.blogging.domain.Followers;
import com.blogging_website.blogging.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FollowersRepository extends CrudRepository<Followers, Long> {
    public ArrayList<Followers> findAllByUserWhoReceiveRequest(Optional<User> user);
    public ArrayList<Followers> findAllByUserWhoReceiveRequestAndRequestAccepted(Optional<User> user,boolean requestAccepted);
    public ArrayList<Followers> findAllByUserWhoSentRequestAndRequestAccepted(Optional<User> user, boolean requestAccepted);
    public Optional<Followers> findByUserWhoReceiveRequestAndUserWhoSentRequest(Optional<User> user,Optional<User> user1);
    public void deleteByUserWhoReceiveRequestAndUserWhoSentRequest(Optional<User> user,Optional<User> user1);
}

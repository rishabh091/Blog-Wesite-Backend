package com.blogging_website.blogging.dao;

import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.Likes;
import com.blogging_website.blogging.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Long> {
    public void deleteByUserAndBlog(Optional<User> user,Optional<Blog> blog);
    public ArrayList<Likes> findAllByUser(Optional<User> user);
    public void deleteByBlog(Optional<Blog> blog);
}

package com.blogging_website.blogging.dao;

import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Integer> {
    public void deleteByBlogId(Integer BlogId);
    public ArrayList<Blog> findAllByAccess(int Private);
    public ArrayList<Blog> findAllByUserAndAccess(Optional<User> user, int access);
    public ArrayList<Blog> findAllByUser(Optional<User> user);
    public ArrayList<Blog> findAllByUserAndDateIsBetween(Optional<User> user, LocalDate date, LocalDate date2);
}

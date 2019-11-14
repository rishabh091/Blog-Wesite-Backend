package com.blogging_website.blogging.dao;

import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.Comments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comments, Long> {

    public ArrayList<Comments> findAllByBlog(Optional<Blog> blog);
    public void deleteByBlog(Optional<Blog> blog);
}

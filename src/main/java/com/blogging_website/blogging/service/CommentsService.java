package com.blogging_website.blogging.service;

import com.blogging_website.blogging.dao.BlogRepository;
import com.blogging_website.blogging.dao.CommentRepository;
import com.blogging_website.blogging.dao.UserRepository;
import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public String addComment(Principal principal,Integer blogId,String comment){
        Comments comments=new Comments();

        try{
            comments.setBlog(blogRepository.findById(blogId).get());
            comments.setUser(userRepository.findByEmail(principal.getName()).get());
            comments.setComment(comment);

            commentRepository.save(comments);
            return "Success";
        }
        catch (Exception e){
            System.err.println(e);
            return "Error occurred";
        }
    }

    public ArrayList<Comments> displayComment(Principal principal,Integer blogId){
        Optional<Blog> blog=blogRepository.findById(blogId);
        ArrayList<Comments> comments=commentRepository.findAllByBlog(blog);
        Collections.reverse(comments);

        return comments;
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}

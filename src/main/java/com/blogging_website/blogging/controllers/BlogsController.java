package com.blogging_website.blogging.controllers;

import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.Likes;
import com.blogging_website.blogging.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/blog")
public class BlogsController {

    @Autowired
    BlogService blogService;

    @GetMapping(path = "/recent",produces = "application/json")
    public ArrayList<Blog> getAllBlogs(Principal principal){
        return blogService.getRecentBlogs();
    }

    @PostMapping(path = "/createBlog", consumes = "application/json")
    public boolean addBlog(@RequestBody Blog blog, Principal principal){
        System.out.println("Adding blog by : "+principal.getName());
        return blogService.addBlog(blog,principal);
    }

    @GetMapping(path = "/getMyBlogs/{userId}", produces = "application/json")
    public ArrayList<Blog> getMyBlogs(Principal principal,@PathVariable Long userId){
        System.err.println("Getting all of the current user blog, User : "+principal.getName()+"Id : "+userId);
        return blogService.getUserBlogs(principal,userId);
    }


    @GetMapping(path = "/deleteBlog/{blogId}",produces = "application/json")
    public boolean deleteBlog(@PathVariable Integer blogId,Principal principal){
        System.out.println("Deleting blog"+blogId+" datatype : "+blogId.getClass().getSimpleName());
        return blogService.deleteBlog(blogId,principal);
    }

    @PostMapping(path = "/updateBlog", consumes = "application/json")
    public String updateBlog(@RequestBody Blog blog,Principal principal){
        System.out.println("Updating blog by "+principal.getName());
        return blogService.updateBlog(blog,principal);
    }

    @GetMapping(path = "/search/{value}", produces = "application/json")
    public ArrayList<Blog> search(@PathVariable String value,Principal principal){
        System.out.println("Searching value..");
        return blogService.search(value);
    }

    @GetMapping(path = "/getBlog/{blogId}", produces = "application/json")
    public Optional<Blog> getBlogById(@PathVariable Integer blogId,Principal principal){
        System.out.println("Getting blog by id : "+blogId);
        return blogService.getBlogById(blogId);
    }

    @GetMapping(path = "/like/{blogId}", produces = "application/json")
    public boolean likeBlog(@PathVariable Integer blogId, Principal principal){
        System.out.println("Liking blog");
        return blogService.likeBlog(blogId,principal);
    }

    @GetMapping(path = "/getLikedBlogs",produces = "application/json")
    public ArrayList<Likes> likedBlogs(Principal principal){
        System.out.println("Sending logged in user his liked blogs..");
        return blogService.likedBlogs(principal);
    }


    @GetMapping(path = "/getCategory", produces = "application/json")
    public ArrayList<String> getCategory(Principal principal){
        System.out.println("Getting all distinct categories");
        return blogService.getCategory(principal);
    }

    @GetMapping(path = "/getBlogFromFollowing", produces = "application/json")
    public ArrayList<Blog> getFollowedBlogs(Principal principal){
        System.out.println("Fetching blogs from people you followed");
        return blogService.getFollowedBlogs(principal);
    }
}

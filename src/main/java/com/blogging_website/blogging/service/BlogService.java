package com.blogging_website.blogging.service;

import com.blogging_website.blogging.dao.*;
import com.blogging_website.blogging.domain.Blog;
import com.blogging_website.blogging.domain.Followers;
import com.blogging_website.blogging.domain.Likes;
import com.blogging_website.blogging.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private FollowersRepository followersRepository;
    @Autowired
    private CommentRepository commentRepository;

    public ArrayList<Blog> getRecentBlogs(){
        ArrayList<Blog> blogList=(ArrayList<Blog>) blogRepository.findAllByAccess(0);
        Collections.reverse(blogList);

        return blogList;
    }

    public boolean addBlog(Blog blog, Principal principal){
        ArrayList<Blog> blogs=(ArrayList<Blog>) blogRepository.findAll();
        //for checking title of blog, if a blog with same title is found user is asked to change the title
        for(int i=0;i<blogs.size();i++){
            Blog currentBlog=blogs.get(i);

            if(currentBlog.getTitle().equals(blog.getTitle())){
                return false;
            }
        }

        Optional<User> user=userRepository.findByEmail(principal.getName());
        blog.setUser(user.get());
        blog.setLikes(0);
        blog.setDate(LocalDate.now());

        blogRepository.save(blog);
        return true;
    }

    public ArrayList<Blog> getUserBlogs(Principal principal,Long userId){
        Optional<User> user=userRepository.findById(userId);
        ArrayList<Blog> blogs;

        if(principal.getName().equals(user.get().getEmail())){
            System.out.println("IS SUPERUSER");
            blogs=blogRepository.findAllByUser(user);
        }
        else{
            System.out.println("NOT SUPERUSER");
            blogs=blogRepository.findAllByUserAndAccess(user,0);
        }

        Collections.reverse(blogs);
        return blogs;
    }

    @Transactional
    public boolean deleteBlog(Integer id,Principal principal){
        Optional<Blog> blog=blogRepository.findById(id);

        likesRepository.deleteByBlog(blog);
        commentRepository.deleteByBlog(blog);
        blogRepository.deleteByBlogId(id);
        return true;
    }

    public String updateBlog(Blog blog,Principal principal){
        Optional<User> user=userRepository.findByEmail(principal.getName());
        blog.setUser(user.get());
        blog.setDate(LocalDate.now());
        blogRepository.save(blog);

        return "\"updation successful\"";
    }

    public ArrayList<Blog> search(String value){
        ArrayList<Blog> blogs=(ArrayList<Blog>) blogRepository.findAllByAccess(0);
        ArrayList<Blog> result=new ArrayList<>();

        for(int i=0;i<blogs.size();i++){
            if(blogs.get(i).getTitle().toLowerCase().contains(value.toLowerCase()) ||
            blogs.get(i).getCategory().toLowerCase().equals(value.toLowerCase()) ||
            blogs.get(i).getSearchDescription().toLowerCase().contains(value.toLowerCase()) ||
            blogs.get(i).getUser().getUserName().toLowerCase().contains(value.toLowerCase())){
                result.add(blogs.get(i));
            }
        }

        return result;
    }

    public Optional<Blog> getBlogById(Integer blogId){
        return blogRepository.findById(blogId);
    }

    @Transactional
    public boolean likeBlog(Integer blogId,Principal principal){
        Optional<User> user=userRepository.findByEmail(principal.getName());
        Optional<Blog> blog=blogRepository.findById(blogId);
        ArrayList<Likes> likesArrayList= (ArrayList<Likes>) likesRepository.findAll();

        //checking if user has already liked that blog, if a user had already liked that blog then
        //it should dislike it and decrease the like count.
        for(int i=0;i<likesArrayList.size();i++){
            if(likesArrayList.get(i).getUser().getUserName().toLowerCase().equals(user.get().getUserName().toLowerCase())
            && likesArrayList.get(i).getBlog().getBlogId().equals(blogId)){
                //dislike
                likesRepository.deleteByUserAndBlog(user,blog);
                //decrementing like count in blog table
                Blog blog1=blog.get();
                if(blog1.getLikes() != 0){
                    blog1.setLikes(blog1.getLikes()-1);
                    System.out.println("Disliking blog..");
                    blogRepository.save(blog1);
                }

                return false;
            }
        }

        Likes likes=new Likes();
        likes.setBlog(blog.get());
        likes.setUser(user.get());
        likesRepository.save(likes);

        //increasing likes in blog table for easy access of number of likes
        Blog blogObject=blog.get();
        blogObject.setLikes(blogObject.getLikes()+1);
        System.out.println("Blog : "+blogObject.toString()+" Likes : "+blogObject.getLikes());
        System.out.println("Saving likes in blog object");
        blogRepository.save(blogObject);

        return true;
    }

    public ArrayList<Likes> likedBlogs(Principal principal){
        Optional<User> user=userRepository.findByEmail(principal.getName());
        return likesRepository.findAllByUser(user);
    }

    public ArrayList<String> getCategory(Principal principal){
        ArrayList<Blog> blogArrayList=getRecentBlogs();
        ArrayList<String> category=new ArrayList<>();

        for(int i=0;i<blogArrayList.size();i++){
            if(!category.contains(blogArrayList.get(i).getCategory())){
                category.add(blogArrayList.get(i).getCategory());
            }
        }

        return category;
    }

    public ArrayList<Blog> getFollowedBlogs(Principal principal){
        ArrayList<Blog> result=new ArrayList<>();
        Optional<User> currentUser=userRepository.findByEmail(principal.getName());

        ArrayList<Followers> followingArrayList=followersRepository.
                findAllByUserWhoSentRequestAndRequestAccepted(currentUser,true);

        ArrayList<User> following=new ArrayList<>();
        for(int i=0;i<followingArrayList.size();i++){
            following.add(followingArrayList.get(i).getUserWhoReceiveRequest());
        }

        for(int i=0;i<following.size();i++){
            Optional<User> user= Optional.ofNullable(following.get(i));
            System.out.println("Previous Date : "+LocalDate.now().minusDays(1));
            System.out.println("Current Date : "+LocalDate.now());
            ArrayList<Blog> blogsOfASingleUser=blogRepository
                    .findAllByUserAndDateIsBetween(user,LocalDate.now().minusDays(2),LocalDate.now());
            result.addAll(blogsOfASingleUser);
        }

        Collections.reverse(result);
        return result;
    }
}

package com.blogging_website.blogging.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "likesTable")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long likeId;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private User user;

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

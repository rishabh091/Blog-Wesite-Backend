package com.blogging_website.blogging.domain;

import javax.persistence.*;

@Entity
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userWhoSentRequest;

    @ManyToOne
    private User userWhoReceiveRequest;

    private boolean requestAccepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserWhoSentRequest() {
        return userWhoSentRequest;
    }

    public void setUserWhoSentRequest(User userWhoSentRequest) {
        this.userWhoSentRequest = userWhoSentRequest;
    }

    public User getUserWhoReceiveRequest() {
        return userWhoReceiveRequest;
    }

    public void setUserWhoReceiveRequest(User userWhoReceiveRequest) {
        this.userWhoReceiveRequest = userWhoReceiveRequest;
    }

    public boolean isRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }
}

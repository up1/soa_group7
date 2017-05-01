package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Post extends PostBase {

    private long id;
    @JsonProperty("comment_count") private int commentCount;
    private int reactions;
    @JsonProperty("created_at") private Date createdAt;
    @JsonProperty("updated_at") private Date updatedAt;
    private int userId;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getReactions() {
        return reactions;
    }

    public void setReactions(int reactions) {
        this.reactions = reactions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}

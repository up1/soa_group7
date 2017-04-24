package com.shenzhentagram.model;

import io.swagger.annotations.ApiModelProperty;

public class User extends UserBase {

    @ApiModelProperty(required = true, position = 1)
    private long id;

    @ApiModelProperty(required = true, position = 2)
    private String username;

    @ApiModelProperty(required = true, position = 7)
    private String role;

    @ApiModelProperty(position = 8)
    private int follows;

    @ApiModelProperty(position = 9)
    private int followed_by;

    @ApiModelProperty(position = 10)
    private int post_count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowed_by() {
        return followed_by;
    }

    public void setFollowed_by(int followed_by) {
        this.followed_by = followed_by;
    }

    public int getPost_count() {
        return post_count;
    }

    public void setPost_count(int post_count) {
        this.post_count = post_count;
    }

}

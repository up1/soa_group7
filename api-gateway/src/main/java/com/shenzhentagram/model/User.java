package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("followed_by")
    @ApiModelProperty(position = 9)
    private int followedBy;

    @JsonProperty("post_count")
    @ApiModelProperty(position = 10)
    private int postCount;

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

    public int getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(int followedBy) {
        this.followedBy = followedBy;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

}

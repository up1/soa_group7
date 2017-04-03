package com.shenzhentagram.model;

public class User {

    private long id;
    private String username;
    private String full_name;
    private String bio;
    private String profile_picture;
    private String display_name;
    private int follows;
    private int followed_by;
    private int post_count;

    public User() { }

    public User(long id, String username, String full_name, String bio, String profile_picture, String display_name, int follows, int followed_by, int post_count) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.bio = bio;
        this.profile_picture = profile_picture;
        this.display_name = display_name;
        this.follows = follows;
        this.followed_by = followed_by;
        this.post_count = post_count;
    }

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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
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

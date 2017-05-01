package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by phompang on 3/6/2017 AD.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String profile_picture;

    @Column(nullable = false)
    private String display_name;

    @Column(nullable = false)
    private int follows;

    @Column(nullable = false)
    private int followed_by;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private int post_count;

    public User() { }

    public User(String username, String password, String full_name, String bio, String profile_picture, String display_name, int follows, int followed_by, int post_count, String role) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.bio = bio;
        this.profile_picture = profile_picture;
        this.display_name = display_name;
        this.follows = follows;
        this.followed_by = followed_by;
        this.post_count = post_count;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

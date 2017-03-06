package com.shenzhentagram.model;

import java.io.Serializable;

/**
 * Created by phompang on 3/6/2017 AD.
 */
public class User implements Serializable {
    private Integer id;
    private String username;
    private String name;
    private String role;

    public User() {
    }

    public User(Integer id, String username, String name, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package com.shenzhentagram.model;

/**
 * Created by Meranote on 3/20/2017.
 */
public class UserRegisterDetail {

    private String username;
    private String password;
    private String full_name;
    private String display_name;

    public UserRegisterDetail() { }

    public UserRegisterDetail(String username, String password, String full_name, String display_name) {
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}

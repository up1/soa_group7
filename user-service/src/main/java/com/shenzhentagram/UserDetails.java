package com.shenzhentagram;

/**
 * Created by phompang on 5/1/2017 AD.
 */
public class UserDetails extends User {
    private String password;

    public UserDetails() {
    }

    public UserDetails(long id, String username, String full_name, String bio, String profile_name, String display_name, int follows, int followed_by, int post_count, String password) {
        super(id, username, full_name, bio, profile_name, display_name, follows, followed_by, post_count);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

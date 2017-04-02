package com.shenzhentagram.model;

/**
 * Created by Meranote on 3/20/2017.
 */
public class UserUpdateDetail {

    private String full_name;
    private String bio;
    private String profile_picture;
    private String display_name;

    public UserUpdateDetail() { }

    public UserUpdateDetail(String full_name, String bio, String profile_picture, String display_name) {
        this.full_name = full_name;
        this.bio = bio;
        this.profile_picture = profile_picture;
        this.display_name = display_name;
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

}

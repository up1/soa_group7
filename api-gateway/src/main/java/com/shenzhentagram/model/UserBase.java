package com.shenzhentagram.model;

import io.swagger.annotations.ApiModelProperty;

public abstract class UserBase {

    @ApiModelProperty(required = true, position = 4)
    private String full_name;

    @ApiModelProperty(position = 5)
    private String bio;

    @ApiModelProperty(position = 6)
    private String profile_picture;

    @ApiModelProperty(required = true, position = 3)
    private String display_name;

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

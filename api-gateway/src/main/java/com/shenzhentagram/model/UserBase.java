package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public abstract class UserBase {

    @JsonProperty("full_name")
    @ApiModelProperty(required = true, position = 4)
    private String fullName;

    @ApiModelProperty(position = 5)
    private String bio;

    @JsonProperty("profile_picture")
    @ApiModelProperty(position = 6)
    private String profilePicture;

    @JsonProperty("display_name")
    @ApiModelProperty(required = true, position = 3)
    private String displayName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}

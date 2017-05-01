package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostUpdate {

    private String caption;
    @JsonProperty("user_id") private long userId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

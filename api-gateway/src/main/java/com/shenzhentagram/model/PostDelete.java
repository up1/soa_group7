package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by phompang on 4/28/2017 AD.
 */
public class PostDelete {
    @JsonProperty("user_id")
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

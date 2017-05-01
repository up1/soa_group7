package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Meranote on 4/1/2017.
 */
public class PostCreate extends PostBase {

    @JsonProperty("user_id")
    private long userId;
    private String file;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}

package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Meranote on 5/1/2017.
 */
public class ReactionBase {

    @JsonProperty("user_id") private long userId;
    private String reaction;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}

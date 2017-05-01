package com.shenzhentagram.model;

/**
 * Created by Meranote on 5/1/2017.
 */
public class ReactionBase {

    private long user_id;
    private String reaction;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}

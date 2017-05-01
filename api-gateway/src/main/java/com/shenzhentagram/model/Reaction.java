package com.shenzhentagram.model;

/**
 * Created by Meranote on 5/1/2017.
 */
public class Reaction extends ReactionBase {

    private int id;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

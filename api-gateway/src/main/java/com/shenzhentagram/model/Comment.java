package com.shenzhentagram.model;

/**
 * Created by Meranote on 4/17/2017.
 */
public class Comment extends CommentBase {

    private String _id;
    private User user;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

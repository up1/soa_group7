package com.shenzhentagram.model;

import java.util.List;

/**
 * Created by Meranote on 4/17/2017.
 */
public class CommentPost {

    List<Comment> comments;

    public CommentPost() { }

    public CommentPost(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

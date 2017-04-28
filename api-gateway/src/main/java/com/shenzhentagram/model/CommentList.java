package com.shenzhentagram.model;

import java.util.List;

/**
 * Created by Meranote on 4/17/2017.<br>
 * Use for receive response from comment service
 */
public class CommentList {

    private int postId;
    private List<Comment> comments;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiravat on 3/13/2017.
 */

public class NotificationPost extends NotificationAbstract {

    @JsonProperty("postId") long postId;
    @JsonProperty("commentId") String commentId;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}

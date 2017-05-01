package com.shenzhentagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiravat on 3/13/2017.
 */

public class NotificationReaction extends NotificationAbstract  {
    @JsonProperty("post_id") long postId;
    @JsonProperty("reactionId") long reactionId;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getReactionId() {
        return reactionId;
    }

    public void setReactionId(long reactionId) {
        this.reactionId = reactionId;
    }
}

package com.shenzhentagram.model;

/**
 * Created by Jiravat on 3/13/2017.
 */

public class NotificationReaction extends NotificationAbstract  {
    long id;
    long post_id;
    long reaction_id;

    public NotificationReaction(long id, long post_id, long reaction_id) {
        this.id = id;
        this.post_id = post_id;
        this.reaction_id = reaction_id;
    }

    public NotificationReaction() {
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public long getReaction_id() {
        return reaction_id;
    }

    public void setReaction_id(long reaction_id) {
        this.reaction_id = reaction_id;
    }
}

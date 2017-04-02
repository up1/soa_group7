package com.shenzhentagram.model;

/**
 * Created by Jiravat on 3/13/2017.
 */

public class NotificationReaction extends NotificationAbstract  {
    long id;
    long reaction_id;

    public NotificationReaction(long id, long reaction_id) {
        this.reaction_id = reaction_id;
    }

    public NotificationReaction() {
    }

    public long getReaction_id() {
        return reaction_id;
    }

    public void setReaction_id(long reaction_id) {
        this.reaction_id = reaction_id;
    }
}

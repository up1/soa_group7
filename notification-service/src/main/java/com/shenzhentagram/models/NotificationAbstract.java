package com.shenzhentagram.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Jiravat on 3/14/2017.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationPost.class, name="comment"),
        @JsonSubTypes.Type(value = NotificationReaction.class, name="reaction"),
        @JsonSubTypes.Type(value = NotificationUser.class, name="followed_by")
})
abstract public class NotificationAbstract {
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

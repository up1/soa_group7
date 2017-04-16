package com.shenzhentagram.services.notification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.sql.Timestamp;

/**
 * Created by Jiravat on 3/9/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    private long id;
    private long userId;
    private String type;
    private String text;
    private String thumbnail;
    private int checkStatus;
    private Timestamp time;

    @JsonIgnore
    private long notificationId;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = NotificationPost.class, name="comment"),
            @JsonSubTypes.Type(value = NotificationReaction.class, name="reaction"),
            @JsonSubTypes.Type(value = NotificationUser.class, name="followed_by")
    })
    private NotificationAbstract from;

    public Notification(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public NotificationAbstract getFrom() {
        return from;
    }

    public void getFrom(NotificationAbstract notification) {
        this.from = from;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Timestamp  getTime() {
        return time;
    }

    public void setTime(Timestamp  time) {
        this.time = time;
    }
}


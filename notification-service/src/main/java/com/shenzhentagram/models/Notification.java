package com.shenzhentagram.models;

/**
 * Created by Jiravat on 3/9/2017.
 */
public class Notification {
    private long id;
    private long userId;
    private String type;
    private String text;
    private String thumbnail;
    private long notificationId;
    private NotificationAbstract notification;

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

    public NotificationAbstract getNotification() {
        return notification;
    }

    public void setNotification(NotificationAbstract notification) {
        this.notification = notification;
    }
}

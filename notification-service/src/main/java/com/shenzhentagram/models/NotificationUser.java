package com.shenzhentagram.models;

/**
 * Created by Jiravat on 3/13/2017.
 */
public class NotificationUser extends NotificationAbstract {
    long userId;

    public NotificationUser() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

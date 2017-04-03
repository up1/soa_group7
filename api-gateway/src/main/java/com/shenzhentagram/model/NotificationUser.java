package com.shenzhentagram.model;

/**
 * Created by Jiravat on 3/13/2017.
 */

public class NotificationUser extends NotificationAbstract {
    long id;
    long userId;

    public NotificationUser(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public NotificationUser() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

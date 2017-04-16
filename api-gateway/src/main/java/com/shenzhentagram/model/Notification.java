package com.shenzhentagram.model;

import java.sql.Timestamp;

/**
 * Created by Meranote on 4/17/2017.
 */
public class Notification extends NotificationBase {

    private int id;

    public Notification(int id, long user_id, String type, String text, String thumbnail, int checkStatus, Timestamp time) {
        super(user_id, type, text, thumbnail, checkStatus, time);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

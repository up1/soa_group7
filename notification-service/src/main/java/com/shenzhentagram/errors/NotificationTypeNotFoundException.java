package com.shenzhentagram.errors;

public class NotificationTypeNotFoundException extends RuntimeException {
    public NotificationTypeNotFoundException(String type) {
        super("Could not find notification type"+type);
    }
}

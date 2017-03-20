package com.shenzhentagram.mappers;

import com.shenzhentagram.models.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Notification notification = new Notification();
        notification.setId(resultSet.getLong("id"));
        notification.setUserId(resultSet.getLong("userId"));
        notification.setText(resultSet.getString("text"));
        notification.setType(resultSet.getString("type_"));
        notification.setNotificationId(resultSet.getLong("notificationId"));
        notification.setThumbnail(resultSet.getString("thumbnail"));
        notification.setCheckStatus(resultSet.getInt("checkStatus"));
        return notification;
    }
}
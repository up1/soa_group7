package com.shenzhentagram.mappers;

import com.shenzhentagram.models.NotificationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jiravat on 3/14/2017.
 */
public class NotificationUserRowMapper implements RowMapper<NotificationUser> {
    @Override
    public NotificationUser mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        NotificationUser notification = new NotificationUser();
        notification.setId(resultSet.getLong("id"));
        notification.setUserId(resultSet.getLong("userId"));
        return notification;
    }
}

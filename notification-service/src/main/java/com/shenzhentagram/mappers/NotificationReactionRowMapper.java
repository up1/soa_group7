package com.shenzhentagram.mappers;

import com.shenzhentagram.models.Notification;
import com.shenzhentagram.models.NotificationReaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jiravat on 3/14/2017.
 */
public class NotificationReactionRowMapper implements RowMapper<NotificationReaction> {
    @Override
    public NotificationReaction mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        NotificationReaction notification = new NotificationReaction();
        notification.setId(resultSet.getLong("id"));
        notification.setReaction_id(resultSet.getLong("reaction_id"));
        return notification;
    }
}
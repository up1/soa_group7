package com.shenzhentagram.mappers;

import com.shenzhentagram.models.NotificationPost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jiravat on 3/14/2017.
 */
public class NotificationPostRowMapper implements RowMapper<NotificationPost> {
    @Override
    public NotificationPost mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        NotificationPost notification = new NotificationPost();
        notification.setId(resultSet.getLong("id"));
        notification.setComment_id(resultSet.getString("comment_id"));
        notification.setPost_id(resultSet.getLong("post_id"));
        return notification;
    }
}

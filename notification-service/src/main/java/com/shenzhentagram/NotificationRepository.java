package com.shenzhentagram;

import com.shenzhentagram.errors.NotificationNotFoundException;
import com.shenzhentagram.mappers.NotificationPostRowMapper;
import com.shenzhentagram.mappers.NotificationReactionRowMapper;
import com.shenzhentagram.mappers.NotificationRowMapper;
import com.shenzhentagram.mappers.NotificationUserRowMapper;
import com.shenzhentagram.models.Notification;
import com.shenzhentagram.models.NotificationPost;
import com.shenzhentagram.models.NotificationReaction;
import com.shenzhentagram.models.NotificationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public Notification findById(Long id) {
        try {
            Notification notification = this.jdbcTemplate.queryForObject(
                    "SELECT id, userId, type_, text, thumbnail, notificationId " +
                            "FROM notifications " +
                            "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new NotificationRowMapper()
            );
            switch (notification.getType()){
                case "followed_by":
                    notification.setNotification(findNotificationUserById(notification.getNotificationId()));
                    break;
                case "comment":
                    notification.setNotification(findNotificationPostById(notification.getNotificationId()));
                    break;
                case "reaction":
                    notification.setNotification(findNotificationReactionById(notification.getNotificationId()));
                    break;
            }
            return notification;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new NotificationNotFoundException(id);
        }
    }

    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    @Transactional(readOnly = true)
    public NotificationPost findNotificationPostById(Long id) {
        try {
            NotificationPost notification = this.jdbcTemplate.queryForObject(
                    "SELECT id, post_id, comment_id " +
                            "FROM notificationPosts " +
                            "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new NotificationPostRowMapper()
            );
            return notification;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new NotificationNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public NotificationUser findNotificationUserById(Long id) {
        try {
            NotificationUser notification = this.jdbcTemplate.queryForObject(
                    "SELECT id, userId " +
                            "FROM notificationUsers " +
                            "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new NotificationUserRowMapper()
            );
            return notification;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new NotificationNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public NotificationReaction findNotificationReactionById(Long id) {
        try {
            NotificationReaction notification = this.jdbcTemplate.queryForObject(
                    "SELECT id, reaction_id " +
                            "FROM notificationUsers " +
                            "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new NotificationReactionRowMapper()
            );
            return notification;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new NotificationNotFoundException(id);
        }
    }

}

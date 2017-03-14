package com.shenzhentagram;

import com.shenzhentagram.errors.NotificationNotFoundException;
import com.shenzhentagram.mappers.NotificationPostRowMapper;
import com.shenzhentagram.mappers.NotificationReactionRowMapper;
import com.shenzhentagram.mappers.NotificationRowMapper;
import com.shenzhentagram.mappers.NotificationUserRowMapper;
import com.shenzhentagram.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public class NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Notification> findByUserId(Long id) {
        try {
            List<Notification> notifications = this.jdbcTemplate.query(
                    "SELECT id, userId, type_, text, thumbnail, notificationId, checkStatus " +
                            "FROM notifications " +
                            "WHERE userId = ?",
                    new Object[] {
                            id
                    },
                    new NotificationRowMapper()
            );
//            System.out.println(notifications);
            notifications.forEach(notification->{
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
            });
            return notifications;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new NotificationNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public Notification findById(Long id) {
        try {
            Notification notification = this.jdbcTemplate.queryForObject(
                    "SELECT id, userId, type_, text, thumbnail, notificationId, checkStatus " +
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
            };
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
                            "FROM notificationReactions " +
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

    @Transactional()
    public int createNotifications(List<Notification> notifications) {
        try {
            String insertSql = "insert into notifications(id,userId ,type_,text,thumbnail,notificationId ,checkStatus) values(?,? ,?,?,?,? ,?)";
            for (Notification notification  : notifications) {
                        this.jdbcTemplate.update(insertSql,
                                new Object[] {
                                        notification.getId(),
                                        notification.getUserId(),
                                        notification.getType(),
                                        notification.getText(),
                                        notification.getThumbnail(),
                                        notification.getNotificationId(),
                                        notification.getCheckStatus()
                                }
                        );
                        switch (notification.getType()){
                            case "followed_by":
                                createNotificationUser((NotificationUser) notification.getNotification());
                                break;
                            case "comment":
                                createNotificationPost((NotificationPost) notification.getNotification());
                                break;
                            case "reaction":
                                createNotificationReaction((NotificationReaction) notification.getNotification());
                                break;
                        }
            }


            return HttpServletResponse.SC_CREATED;
        } catch (Exception exception) {
            System.out.print(exception);
            return HttpServletResponse.SC_NOT_MODIFIED;
        }
    }

    @Transactional()
    public void createNotificationUser(NotificationUser notificationUser) {
        try {
            String insertSql = "insert into notificationUsers(id, userId) values(?,?)";
            this.jdbcTemplate.update(insertSql,
                                new Object[] {
                                        notificationUser.getId(),
                                        notificationUser.getUserId()
                                }
                        );
        } catch (Exception exception) {
            throw  exception;
        }
    }

    @Transactional()
    public void createNotificationPost(NotificationPost notificationPost) {
        try {
            String insertSql = "insert into notificationPosts(id, post_id ,comment_id) values(?,? ,?)";
            this.jdbcTemplate.update(insertSql,
                    new Object[] {
                            notificationPost.getId(),
                            notificationPost.getPost_id(),
                            notificationPost.getComment_id()

                    }
            );
        } catch (Exception exception) {
            throw  exception;
        }
    }

    @Transactional()
    public void createNotificationReaction(NotificationReaction notificationReaction) {
        try {
            String insertSql = "insert into notificationReactions(id, reaction_id) values(?,?)";
            this.jdbcTemplate.update(insertSql,
                    new Object[] {
                            notificationReaction.getId(),
                            notificationReaction.getReaction_id()

                    }
            );
        } catch (Exception exception) {
            throw  exception;
        }
    }




}

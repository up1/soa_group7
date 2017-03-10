package com.shenzhentagram;

import com.shenzhentagram.errors.NotificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public Notification findById(Long id) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT id, type_, text, thumbnail, notificationId " +
                            "FROM notifications" +
                            "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new NotificationRowMapper()
            );
        } catch (Exception exception) {
            throw new NotificationNotFoundException(id);
        }
    }

    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

}

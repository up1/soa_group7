package com.shenzhentagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT id, username, full_name, bio, profile_picture, display_name, role, follows, followed_by, post_count " +
                        "FROM users " +
                        "WHERE id = ?",
                    new Object[] {
                            id
                    },
                    new UserRowMapper()
            );
        } catch (Exception exception) {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT id, username, full_name, bio, profile_picture, display_name, role, follows, followed_by, post_count " +
                            "FROM users " +
                            "WHERE username = ?",
                    new Object[] {
                            username
                    },
                    new UserRowMapper()
            );
        } catch (Exception exception) {
            throw new UserNotFoundException(username);
        }
    }

    @Transactional(readOnly = true)
    public List<User> findByName(String name) {
        try {
            return this.jdbcTemplate.query(
                    "SELECT id, username, full_name, bio, profile_picture, display_name, role, follows, followed_by, post_count " +
                            "FROM users " +
                            "WHERE full_name LIKE '%?%' " +
                            "OR display_name LIKE '%?%' " +
                            "OR username LIKE '%?%'",
                    new Object[] {
                            name, name, name
                    },
                    new UserRowMapper()
            );
        } catch (Exception exception) {
            throw new UserNotFoundException(name);
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll(int page, int itemPerPage) {
        int countRow = this.jdbcTemplate.queryForObject("SELECT count(*) FROM users", int.class);
        int maxPage = (int) Math.ceil(countRow/(double) itemPerPage);

        if (page < 1 || itemPerPage < 1 || page > maxPage) {
            throw new InvalidArgumentsException();
        }

        return this.jdbcTemplate.query(
                "SELECT id, username, full_name, bio, profile_picture, display_name, role, follows, followed_by, post_count " +
                    "FROM users " +
                    "LIMIT ?, ?",
                new Object[] {
                        (page-1)*itemPerPage, itemPerPage
                },
                new UserRowMapper()
        );
    }

    @Transactional
    public void save(User user, String password) {
        String sql = "INSERT INTO " +
                "users(username, password, full_name, bio, profile_picture, display_name, follows, followed_by, post_count, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        this.jdbcTemplate.update(
                sql,
                user.getUsername(),
                password,
                user.getFull_name(),
                user.getBio(),
                user.getProfile_picture(),
                user.getDisplay_name(),
                0,
                0,
                0,
                "USER"
        );
    }

    @Transactional
    public void update(User user) {
        String sql = "UPDATE users " +
                "SET full_name = ?, bio = ?, display_name = ?, profile_picture = ?, follows = ?, followed_by = ?, post_count = ? " +
                "WHERE id = ?";
        try{
            this.jdbcTemplate.update(
                    sql,
                    user.getFull_name(),
                    user.getBio(),
                    user.getDisplay_name(),
                    user.getProfile_picture(),
                    user.getFollows(),
                    user.getFollowed_by(),
                    user.getPost_count(),
                    user.getId()
            );
        } catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

}

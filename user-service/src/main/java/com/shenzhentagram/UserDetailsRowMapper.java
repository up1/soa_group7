package com.shenzhentagram;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by phompang on 5/1/2017 AD.
 */
public class UserDetailsRowMapper implements RowMapper<UserDetails> {
    @Override
    public UserDetails mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        UserDetails user = new UserDetails();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setFull_name(resultSet.getString("full_name"));
        user.setBio(resultSet.getString("bio"));
        user.setProfile_picture(resultSet.getString("profile_picture"));
        user.setDisplay_name(resultSet.getString("display_name"));
        user.setRole(resultSet.getString("role"));
        user.setFollows(resultSet.getInt("follows"));
        user.setFollowed_by(resultSet.getInt("followed_by"));
        user.setPost_count(resultSet.getInt("post_count"));
        return user;
    }
}

package com.shenzhentagram;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setBio(resultSet.getString("bio"));
        user.setProfile_picture(resultSet.getString("profile_picture"));
        user.setDisplay_name(resultSet.getString("display_name"));
        user.setFollows(resultSet.getInt("follows"));
        user.setFollowed_by(resultSet.getInt("followed_by"));
        return user;
    }
}

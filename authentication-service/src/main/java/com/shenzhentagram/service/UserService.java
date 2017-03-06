package com.shenzhentagram.service;

import com.shenzhentagram.model.User;

import java.util.Optional;

/**
 * Created by phompang on 3/6/2017 AD.
 */
public interface UserService {
    public Optional<User> getByUsername(String username);
}

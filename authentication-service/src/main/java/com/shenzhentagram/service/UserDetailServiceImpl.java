package com.shenzhentagram.service;

import com.shenzhentagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by phompang on 5/1/2017 AD.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> u = userService.getByUsername(s);
        if (!u.isPresent()) {
            throw new UsernameNotFoundException(s);
        }
        return new org.springframework.security.core.userdetails.User(u.get().getUsername(), u.get().getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

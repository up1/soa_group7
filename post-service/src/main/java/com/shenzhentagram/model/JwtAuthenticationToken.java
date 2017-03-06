package com.shenzhentagram.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by phompang on 3/6/2017 AD.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String token;
    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return null;
    }
    @Override
    public Object getPrincipal() {
        return null;
    }
}

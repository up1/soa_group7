package com.shenzhentagram.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * AuthenticatedUser for Authentication information
 *
 * @author Meranote
 */
public class AuthenticatedUser implements Authentication {

    private long id;
    private String name;
    private String role;
    private boolean authenticated = true;

    public AuthenticatedUser(long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }

}

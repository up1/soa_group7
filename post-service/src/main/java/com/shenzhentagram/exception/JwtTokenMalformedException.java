package com.shenzhentagram.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by phompang on 3/6/2017 AD.
 */
public class JwtTokenMalformedException extends AuthenticationException {
    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}

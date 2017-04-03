package com.shenzhentagram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by phompang on 3/19/2017 AD.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserIdNotMatchException extends RuntimeException {
    public UserIdNotMatchException(String message) {
        super(message);
    }
}

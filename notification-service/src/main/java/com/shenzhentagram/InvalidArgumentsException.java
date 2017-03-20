package com.shenzhentagram;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by phompang on 2/6/2017 AD.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Invalid Arguments");
    }
}

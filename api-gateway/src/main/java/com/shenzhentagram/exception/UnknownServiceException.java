package com.shenzhentagram.exception;

/**
 * Created by Meranote on 4/28/2017.
 */
public class UnknownServiceException extends Exception {

    public UnknownServiceException(String serviceName) {
        super("Unknown service => " + serviceName);
    }
}

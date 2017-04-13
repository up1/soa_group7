package com.shenzhentagram.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Meranote on 4/13/2017.
 */
public class RestTemplateException extends RuntimeException {

    private long timestamps;
    private HttpStatus httpStatus;
    private String path;
    private String service;

    public RestTemplateException(long timestamps, HttpStatus httpStatus, String message, String path, String service) {
        super(message);

        this.timestamps = timestamps;
        this.httpStatus = httpStatus;
        this.path = path;
        this.service = service;
    }

    public long getTimestamps() {
        return timestamps;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getPath() {
        return path;
    }

    public String getService() {
        return service;
    }
}

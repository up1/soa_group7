package com.shenzhentagram.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Meranote on 4/13/2017.
 */
public class RestTemplateException extends RuntimeException {

    private long timestamps;
    private HttpStatus httpStatus;
    private String message;
    private String path;
    private String service;

    public RestTemplateException(RestClientResponseException ex, String path, String service) {
        try {
            HashMap<String, Object> responseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), HashMap.class);
            this.timestamps = (long) responseBody.get("timestamp");
            this.httpStatus = HttpStatus.valueOf((int) responseBody.get("status"));
            this.message = (String) responseBody.get("message");
        } catch (IOException e) {
            this.timestamps = new Date().getTime();
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            this.message = "Cannot extract error body: " + ex.getResponseBodyAsString();
        }

        this.path = path;
        this.service = service;
    }

    public RestTemplateException(ResourceAccessException ex, String path, String service) {
        this.timestamps = new Date().getTime();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = ex.getMessage();
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

    @Override
    public String getMessage() {
        return this.message;
    }

}

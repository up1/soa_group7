package com.shenzhentagram.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateException.class);

    private final long timestamps;
    private final HttpStatus httpStatus;
    private final String message;
    private final String path;
    private final String service;

    public RestTemplateException(RestClientResponseException ex, String path, String service) {
        String tMessage;
        HttpStatus tHttpStatus;
        long tTimestamps;
        try {
            HashMap<String, Object> responseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), HashMap.class);
            tTimestamps = (long) responseBody.get("timestamp");
            tHttpStatus = HttpStatus.valueOf((int) responseBody.get("status"));
            tMessage = (String) responseBody.get("message");
        } catch (IOException e) {
            logger.debug("IOException", e);

            tTimestamps = new Date().getTime();
            tHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            tMessage = "Cannot extract error body: " + ex.getResponseBodyAsString();
        }

        this.message = tMessage;
        this.httpStatus = tHttpStatus;
        this.timestamps = tTimestamps;
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

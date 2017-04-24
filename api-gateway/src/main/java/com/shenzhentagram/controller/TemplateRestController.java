package com.shenzhentagram.controller;

import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.exception.RestTemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

/**
 * Template Rest Controller<br>
 * Use for creating a REST request controller to other service<br>
 * Setting for calling to the other services can be found in resources/application.properties<br><br>
 *
 * Example setting for each service in application.properties:<br>
 * service.[service-name].protocol = http || https<br>
 * service.[service-name].port = [current-service-port]<br>
 * service.[service-name].ip = [current-service-ip]
 *
 * @author Meranote
 * @version 2.0
 */
public abstract class TemplateRestController {

    /**
     * Service Name
     */
    private String serviceName;

    /**
     * Logging
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * URI Template Handler (For logging)
     */
    private UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();

    /**
     * Rest template for calling apis
     */
    protected RestTemplate restTemplate;

    /**
     * Default timeout
     */
    private static final int TIMEOUT = 20 * 1000; // in milliseconds, 20 seconds

    /**
     * Setup the template for REST request
     * @param environment
     * @param restTemplateBuilder
     * @param serviceName
     */
    public TemplateRestController(Environment environment, RestTemplateBuilder restTemplateBuilder, String serviceName) {
        this.serviceName = serviceName;

        String protocol = environment.getProperty("service." + serviceName + ".protocol");
        String ip = environment.getProperty("service." + serviceName + ".ip");
        String port = environment.getProperty("service." + serviceName + ".port");

        this.restTemplate = restTemplateBuilder.rootUri(protocol + "://" + ip + ":" + port).build();

        // Set components (request timeout)
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);

        this.restTemplate.setRequestFactory(requestFactory);
    }

    /**
     * Send a request (without Authorization)
     * @param method {@link HttpMethod} to call service
     * @param uri path to call service
     * @param responseClass target class that will map the response body into
     * @param uriVariables path variables
     * @param <T> target class that will map the response body into
     * @return {@link ResponseEntity} => mapped response object
     */
    protected <T> ResponseEntity<T> request(HttpMethod method, String uri, Class<T> responseClass, Object... uriVariables) {
        return request(method, uri, "", responseClass, uriVariables);
    }

    /**
     * Send a request (without Authorization)
     * @param method {@link HttpMethod} to call service
     * @param uri path to call service
     * @param responseClass target class that will map the response body into
     * @param body request body
     * @param uriVariables path variables
     * @param <T> target class that will map the response body into
     * @return {@link ResponseEntity} => mapped response object
     */
    protected <T> ResponseEntity<T> request(HttpMethod method, String uri, Object body, Class<T> responseClass, Object... uriVariables) {
        HttpEntity<Object> entity = new HttpEntity<>(body, new HttpHeaders());

        try {
            return restTemplate.exchange(uri, method, entity, responseClass, uriVariables);
        } catch(RestClientResponseException e) {
            String targetPath = uriTemplateHandler.expand(uri, uriVariables).getPath();
            log.error("Error calling " + method.toString() + " '" + targetPath + "' : " + e.getRawStatusCode() + " " + e.getStatusText());
            throw new RestTemplateException(e, targetPath, serviceName);
        }
    }

    /**
     * Get the current authenticated user
     * @return current {@link AuthenticatedUser}
     */
    protected AuthenticatedUser getAuthenticatedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Exception message format
     */
    public static class ExceptionMessage {

        private long timestamp;
        private int status;
        private String error;
        private String message;
        private String service;
        private String path;

        public ExceptionMessage(RestTemplateException exception) {
            this.timestamp = exception.getTimestamps();
            this.status = exception.getHttpStatus().value();
            this.error = exception.getHttpStatus().getReasonPhrase();
            this.message = exception.getMessage();
            this.service = exception.getService();
            this.path = exception.getPath();
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    /**
     * {@link RestTemplateException} handler
     * @param e Exception object throw by request()
     * @return {@link ExceptionMessage}
     */
    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ExceptionMessage> restRequestExceptionHandler(RestTemplateException e) {
        return new ResponseEntity<>(new ExceptionMessage(e), e.getHttpStatus());
    }

}

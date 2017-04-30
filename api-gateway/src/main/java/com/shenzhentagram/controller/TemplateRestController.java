package com.shenzhentagram.controller;

import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.exception.RestTemplateException;
import com.shenzhentagram.scheduler.ServiceConnectingTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

import java.util.Date;
import java.util.regex.Matcher;

import static com.shenzhentagram.prometheus.RequestCounter.*;

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
 * @version 3
 */
public abstract class TemplateRestController {

    @Autowired
    private ServiceConnectingTask serviceConnectingTask;

    /**
     * Service Name
     */
    private String serviceName;

    /**
     * Logging
     */
    protected Log log = LogFactory.getLog(this.getClass());

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
        String targetPath = uriTemplateHandler.expand(uri, uriVariables).getPath();

        if(serviceConnectingTask.isServiceAlive(serviceName)) {
            HttpEntity<Object> entity = new HttpEntity<>(body, new HttpHeaders());
            try {
                ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, entity, responseClass, uriVariables);

                //prometheus count
                int SC = responseEntity.getStatusCodeValue();
                countRequestRequest(SC);

                if(uri.matches("/auth")){
                    countAuthenticationRequest(SC);
                }
                else if(uri.matches("/posts.*./comments.*")){
                    countCommentRequest(SC);
                }
                else if(uri.matches("/notifications.*")){
                    countNotificationRequest(SC);
                }
                else if(uri.matches("/posts.*")){
                    countPostRequest(SC);
                }
                else if(uri.matches("/users.*")){
                    countUserRequest(SC);
                }


                return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
            } catch (RestClientResponseException e) {
                log.error("Error calling " + method.toString() + " '" + targetPath + "' : " + e.getRawStatusCode() + " " + e.getStatusText());
                throw new RestTemplateException(e, targetPath, serviceName);
            } catch (ResourceAccessException e) {
                log.error("Error calling " + method.toString() + " '" + targetPath + "' : cannot access to service");
                serviceConnectingTask.forceSetServiceState(serviceName, false);
                throw new RestTemplateException(e, targetPath, serviceName);
            }
        } else {
            throw new RestTemplateException(new ResourceAccessException("Service is not connected"), targetPath, serviceName);
        }
    }

    /**
     * Run request without throwing any error (Request exception wrapper)
     * @param runnable
     * @return
     */
    protected void guardRequester(Runnable runnable) {
        guardRequester(runnable, null);
    }

    /**
     * Run request without throwing any error (Request exception wrapper)
     * @param runnable
     * @param errorLogMessage
     * @return
     */
    protected void guardRequester(Runnable runnable, String errorLogMessage) {
        try {
            runnable.run();
        } catch (Exception ignored) {
            if (errorLogMessage != null) {
                log.warn(errorLogMessage, ignored);
            }
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

        public ExceptionMessage(long timestamp, int status, String error, String message, String service, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.service = service;
            this.path = path;
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

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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

import static com.shenzhentagram.prometheus.RequestCounter.*;

public abstract class TemplateRestController {

    @Autowired
    private ServiceConnectingTask serviceConnectingTask;

    private String serviceName;

    protected Log log = LogFactory.getLog(this.getClass());

    private UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();

    protected RestTemplate restTemplate;

    private static final int TIMEOUT = 20 * 1000; // in milliseconds, 20 seconds

    public TemplateRestController(Environment environment, RestTemplateBuilder restTemplateBuilder, String serviceName) {
        this.serviceName = serviceName;

        String service = "service.";
        String protocol = environment.getProperty(service + serviceName + ".protocol");
        String ip = environment.getProperty(service + serviceName + ".ip");
        String port = environment.getProperty(service + serviceName + ".port");

        this.restTemplate = restTemplateBuilder.rootUri(protocol + "://" + ip + ":" + port).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);

        this.restTemplate.setRequestFactory(requestFactory);
    }

    protected <T> ResponseEntity<T> request(HttpMethod method, String uri, Class<T> responseClass, Object... uriVariables) {
        return request(method, uri, "", responseClass, uriVariables);
    }

    protected <T> ResponseEntity<T> request(HttpMethod method, String uri, Object body, Class<T> responseClass, Object... uriVariables) {
        String targetPath = uriTemplateHandler.expand(uri, uriVariables).getPath();
        if(serviceConnectingTask.isServiceAlive(serviceName)) {
            HttpEntity<Object> entity = new HttpEntity<>(body, new HttpHeaders());
            try {
                ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, entity, responseClass, uriVariables);

                int sc = responseEntity.getStatusCodeValue();
                countRequestRequest(sc);

                if(uri.matches("/auth")){
                    countAuthenticationRequest(sc);
                }
                else if(uri.matches("/posts.*./comments.*")){
                    countCommentRequest(sc);
                }
                else if(uri.matches("/notifications.*")){
                    countNotificationRequest(sc);
                }
                else if(uri.matches("/posts.*")){
                    countPostRequest(sc);
                }
                else if(uri.matches("/users.*./follow.*")){
                    countFollowRequest(sc);
                }
                else if(uri.matches("/users.*")){
                    countUserRequest(sc);
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
            countRequestRequest(503);
            if(uri.matches("/auth")){
                countAuthenticationRequest(503);
            }
            else if(uri.matches("/posts.*./comments.*")){
                countCommentRequest(503);
            }
            else if(uri.matches("/notifications.*")){
                countNotificationRequest(503);
            }
            else if(uri.matches("/posts.*")){
                countPostRequest(503);
            }
            else if(uri.matches("/users.*./follow.*")){
                countFollowRequest(503);
            }
            else if(uri.matches("/users.*")){
                countUserRequest(503);
            }

            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Response:"+targetPath+", Service "+serviceName+" Unavailable");
        }
    }

    protected void guardRequester(Runnable runnable) {
        guardRequester(runnable, null);
    }

    protected void guardRequester(Runnable runnable, String errorLogMessage) {
        try {
            runnable.run();
        } catch (Exception ignored) {
            if (errorLogMessage != null) {
                log.warn(errorLogMessage, ignored);
            }
        }
    }

    protected AuthenticatedUser getAuthenticatedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }

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

    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ExceptionMessage> restRequestExceptionHandler(RestTemplateException e) {
        return new ResponseEntity<>(new ExceptionMessage(e), e.getHttpStatus());
    }

}

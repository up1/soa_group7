package com.shenzhentagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.authentication.JWTAuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import org.springframework.web.util.UriTemplateHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
 * @version 1.2
 */
public abstract class TemplateRestController {

    /**
     * Logging
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * URI Template Handler (For logging)
     */
    UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();

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
     * Extract the request body into object
     * @param request {@link HttpServletRequest}
     * @param mapToClass class to map into
     * @param <T> class to map into
     * @return mapped body object
     * @throws IOException
     */
    @Deprecated
    protected <T> T extractBody(HttpServletRequest request, Class<T> mapToClass) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), mapToClass);
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

        // For logging
        String targetPath = uriTemplateHandler.expand(uri, uriVariables).getPath();

        try {
            return restTemplate.exchange(uri, method, entity, responseClass, uriVariables);
        } catch(RestClientResponseException e) {
            log.error("Error calling  " + method.toString() + " '" + targetPath + "' : " + e.getRawStatusCode() + " " + e.getStatusText());
            return new ResponseEntity<>((T) null, HttpStatus.valueOf(e.getRawStatusCode()));
        }
    }

    /**
     * Send a request (with Authorization)
     * @param method {@link HttpMethod} to call service
     * @param uri path to call service
     * @param responseClass target class that will map the response body into
     * @param uriVariables path variables
     * @param <T> target class that will map the response body into
     * @return {@link ResponseEntity} => mapped response object
     */
    @Deprecated
    protected <T> ResponseEntity<T> requestWithAuth(HttpMethod method, String uri, Class<T> responseClass, Object... uriVariables) {
        return requestWithAuth(method, uri, "", responseClass, uriVariables);
    }

    /**
     * Send a request (with Authorization)
     * @param method {@link HttpMethod} to call service
     * @param uri path to call service
     * @param responseClass target class that will map the response body into
     * @param body request body
     * @param uriVariables path variables
     * @param <T> target class that will map the response body into
     * @return {@link ResponseEntity} => mapped response object
     */
    @Deprecated
    protected <T> ResponseEntity<T> requestWithAuth(HttpMethod method, String uri, Object body, Class<T> responseClass, Object... uriVariables) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();

        HttpHeaders headers = new HttpHeaders();
        headers.set(
                JWTAuthenticationService.HEADER_STRING,
                JWTAuthenticationService.TOKEN_PREFIX + " " + authenticatedUser.getToken()
        );
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        try {
            log.debug(new ObjectMapper().writeValueAsString(body));
        } catch (JsonProcessingException e) {
            log.trace(e);
        }

        return restTemplate.exchange(uri, method, entity, responseClass, uriVariables);
    }

    /**
     * Get the current authenticated user
     * @return current {@link AuthenticatedUser}
     */
    protected AuthenticatedUser getAuthenticatedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }

}

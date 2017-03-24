package com.shenzhentagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Meranote on 3/20/2017.
 */
public abstract class TemplateRestController {

    protected RestTemplate restTemplate;

    public TemplateRestController(Environment environment, RestTemplateBuilder restTemplateBuilder, String serviceName) {
        String protocol = environment.getProperty("service." + serviceName + ".protocol");
        String ip = environment.getProperty("service." + serviceName + ".ip");
        String port = environment.getProperty("service." + serviceName + ".port");

        this.restTemplate = restTemplateBuilder.rootUri(protocol + "://" + ip + ":" + port).build();
    }

    protected <T> T extractBody(HttpServletRequest request, Class<T> tClass) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), tClass);
    }

}

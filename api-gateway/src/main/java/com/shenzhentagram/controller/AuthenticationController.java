package com.shenzhentagram.controller;

import com.shenzhentagram.model.AuthenticateCredential;
import com.shenzhentagram.model.AuthenticateDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthenticationController {

    private final RestTemplate restTemplate;

    @Value("${service.authentication.ip}")
    private String ip;

    @Value("${service.authentication.port}")
    private String port;

    @Value("${service.authentication.protocol:http}")
    private String protocol;

    public AuthenticationController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public AuthenticateDetail auth(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        String path = protocol + "://" + ip + ":" + port;
        return restTemplate.postForObject(path + "/auth", new AuthenticateCredential(username, password), AuthenticateDetail.class);
    }

}

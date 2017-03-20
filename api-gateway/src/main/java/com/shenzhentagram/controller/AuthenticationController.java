package com.shenzhentagram.controller;

import com.shenzhentagram.model.AuthenticateCredential;
import com.shenzhentagram.model.AuthenticateDetail;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Meranote on 3/20/2017.
 */
@RestController
public class AuthenticationController extends TemplateRestController {

    public AuthenticationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "authentication");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public AuthenticateDetail auth(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        return restTemplate.postForObject("/auth", new AuthenticateCredential(username, password), AuthenticateDetail.class);
    }

}

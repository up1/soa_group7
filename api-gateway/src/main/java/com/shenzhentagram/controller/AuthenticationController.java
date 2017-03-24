package com.shenzhentagram.controller;

import com.shenzhentagram.model.AuthenticateCredential;
import com.shenzhentagram.model.AuthenticateDetail;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController extends TemplateRestController {

    public AuthenticationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "authentication");
    }

    @PostMapping()
    public AuthenticateDetail auth(HttpServletRequest request) throws IOException {
        return restTemplate.postForObject("/auth", extractBody(request, AuthenticateCredential.class), AuthenticateDetail.class);
    }

}

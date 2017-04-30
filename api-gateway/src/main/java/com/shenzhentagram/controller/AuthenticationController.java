package com.shenzhentagram.controller;

import com.shenzhentagram.model.AuthenticateCredential;
import com.shenzhentagram.model.AuthenticateDetail;
import com.shenzhentagram.prometheus.RequestCounter;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController extends TemplateRestController {

    public AuthenticationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "authentication");
    }

    @PostMapping()
    @ApiOperation(
            tags = "Authenticate-API",
            value = "auth",
            nickname = "auth",
            notes = "Authenticate (Request token)"
    )
    public ResponseEntity<AuthenticateDetail> auth(
            @RequestBody AuthenticateCredential credential
    ) {
        ResponseEntity<AuthenticateDetail> auth = request(HttpMethod.POST, "/auth", credential, AuthenticateDetail.class);
        RequestCounter.countAuthenRequest(auth.getStatusCodeValue());
        return auth;
    }

}

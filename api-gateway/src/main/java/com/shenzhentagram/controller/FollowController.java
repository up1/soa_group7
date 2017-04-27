package com.shenzhentagram.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;

/**
 * Created by Meranote on 4/27/2017.
 */
public class FollowController extends TemplateRestController {

    /**
     * Setup the template for REST request
     *
     * @param environment
     * @param restTemplateBuilder
     */
    public FollowController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "follow");
    }

}

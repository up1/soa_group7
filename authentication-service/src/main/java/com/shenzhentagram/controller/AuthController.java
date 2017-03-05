package com.shenzhentagram.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Meranote on 3/5/2017.
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @RequestMapping(method = RequestMethod.GET, path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getUsers() {
        return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
    }

}

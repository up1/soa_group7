package com.shenzhentagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Meranote on 3/5/2017.
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @RequestMapping(method = RequestMethod.GET, path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getUsers() {
        // FIXME Get users from Users-DB
        // TODO For test only

        return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String authenticate(@RequestAttribute("access_token") String access_token) throws JsonProcessingException {
        HashMap<String, Object> mappingResponse = new HashMap<String, Object>() {{
            put("access_token", access_token);
        }};
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(mappingResponse);
    }

}

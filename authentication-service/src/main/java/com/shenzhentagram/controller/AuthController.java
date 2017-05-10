package com.shenzhentagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.model.User;
import com.shenzhentagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Meranote on 3/5/2017.
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getUsers() throws JsonProcessingException {

        AuthenticatedUser auth = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String authenticate(
            @RequestAttribute("access_token") String access_token,
            @RequestAttribute("authenticated_user") User user
    ) throws JsonProcessingException {
        HashMap<String, Object> mappingResponse = new HashMap<>();
        mappingResponse.put("access_token", access_token);
        mappingResponse.put("user", user);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(mappingResponse);
    }

}

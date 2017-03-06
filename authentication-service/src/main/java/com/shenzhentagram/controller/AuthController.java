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

    /**
     * Get token's user details <b>(For test only)</b>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String getUsers() throws JsonProcessingException {
        // TODO For test only

        // Get authenticated user
        AuthenticatedUser auth = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();

        // Get user by username
        String username = auth.getName();
        User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }

    /**
     * Authenticate user
     * @see com.shenzhentagram.filter.JWTLoginFilter
     */
    @RequestMapping(method = RequestMethod.POST, path = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String authenticate(
            @RequestAttribute("access_token") String access_token,
            @RequestAttribute("authenticated_user") User user
    ) throws JsonProcessingException {
        HashMap<String, Object> mappingResponse = new HashMap<String, Object>() {{
            put("access_token", access_token);
            put("user", user);
        }};
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(mappingResponse);
    }

}

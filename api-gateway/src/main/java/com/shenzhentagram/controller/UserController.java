package com.shenzhentagram.controller;

import com.shenzhentagram.model.UserRegisterDetail;
import com.shenzhentagram.model.User;
import com.shenzhentagram.model.UserUpdateDetail;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Meranote on 3/20/2017.
 */
@RestController
public class UserController extends TemplateRestController {

    public UserController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "user");
    }

    @RequestMapping(method = RequestMethod.GET , path = "/users/{user_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public User getUser(
            @PathVariable("user_id") long id
    ) {
        return restTemplate.getForObject("/users/{user_id}", User.class, id);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/users/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return restTemplate.getForObject("/users/search?name" + name, List.class);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void createUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("full_name") String full_name,
            @RequestParam("bio") String bio,
            @RequestParam("profile_picture") String profile_picture,
            @RequestParam("display_name") String display_name
    ){
        restTemplate.postForObject("/users", new UserRegisterDetail(email, password, full_name, bio, profile_picture, display_name), Void.class);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/self", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> getSelf(
            HttpServletRequest request
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return restTemplate.exchange("/users/self", HttpMethod.GET, entity, String.class);
    }

    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/users/self", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void updateSelf(
            @RequestParam("full_name") String full_name,
            @RequestParam("bio") String bio,
            @RequestParam("profile_picture") String profile_picture,
            @RequestParam("display_name") String display_name
    ) {
        restTemplate.put("/users/self", new UserUpdateDetail(full_name, bio, profile_picture, display_name), Void.class);
    }

}

package com.shenzhentagram.controller;

import com.shenzhentagram.model.UserRegisterDetail;
import com.shenzhentagram.model.User;
import com.shenzhentagram.model.UserUpdateDetail;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/users")
public class UserController extends TemplateRestController {

    public UserController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "user");
    }

    @GetMapping(path = "/{user_id}")
    public User getUser(
            @PathVariable("user_id") long id
    ) {
        return restTemplate.getForObject("/users/{user_id}", User.class, id);
    }

    @GetMapping(path = "/search")
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return restTemplate.getForObject("/users/search?name" + name, List.class);
    }

    @PostMapping()
    public void createUser(HttpServletRequest request) throws IOException {
        restTemplate.postForObject("/users", extractBody(request, UserRegisterDetail.class), Void.class);
    }

    @GetMapping(path = "/self")
    public ResponseEntity<String> getSelf(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return restTemplate.exchange("/users/self", HttpMethod.GET, entity, String.class);
    }

    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/self")
    public void updateSelf(HttpServletRequest request) throws IOException {
        restTemplate.put("/users/self", extractBody(request, UserUpdateDetail.class), Void.class);
    }

}

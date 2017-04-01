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
        return request(HttpMethod.GET, "/users/{user_id}", User.class, id);
    }

    @GetMapping(path = "/search")
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return request(HttpMethod.GET, "/users/search?name" + name, List.class);
    }

    @PostMapping()
    public void createUser(HttpServletRequest request) throws IOException {
        request(HttpMethod.POST, "/users", extractBody(request, UserRegisterDetail.class), Void.class);
    }

    @GetMapping(path = "/self")
    public User getSelf() {
        return requestWithAuth(HttpMethod.GET, "/users/self", User.class);
    }

    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/self")
    public void updateSelf(HttpServletRequest request) throws IOException {
        request(HttpMethod.PUT, "/users/self", extractBody(request, UserUpdateDetail.class), Void.class);
    }

}

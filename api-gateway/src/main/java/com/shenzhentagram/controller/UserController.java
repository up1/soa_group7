package com.shenzhentagram.controller;

import com.shenzhentagram.model.UserPostCount;
import com.shenzhentagram.model.UserRegisterDetail;
import com.shenzhentagram.model.User;
import com.shenzhentagram.model.UserUpdateDetail;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController extends TemplateRestController {

    public UserController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "user");
    }

    @GetMapping("/{id}")
    public User getUser(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/users/{id}", User.class, id);
    }

    @GetMapping("/search")
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return request(HttpMethod.GET, "/users/search?name" + name, List.class);
    }

    @PostMapping()
    public void createUser(HttpServletRequest request) throws IOException {
        request(HttpMethod.POST, "/users", extractBody(request, UserRegisterDetail.class), Void.class);
    }

    @GetMapping("/self")
    public User getSelf() {
        return requestWithAuth(HttpMethod.GET, "/users/self", User.class);
    }

    @PatchMapping(path = "/self")
    public void updateSelf(HttpServletRequest request) throws IOException {
        request(HttpMethod.PATCH, "/users/self", extractBody(request, UserUpdateDetail.class), Void.class);
    }

    /**
     * [Internal only] Increase user posts count by one
     */
    public UserPostCount increasePosts(int id) {
        return request(HttpMethod.POST, "/users/{id}/posts/count", UserPostCount.class, id);
    }

    /**
     * [Internal only] Decrease user posts count by one
     */
    public UserPostCount decreasePosts(int id) {
        return request(HttpMethod.PUT, "/users/{id}/posts/count", UserPostCount.class, id);
    }

}

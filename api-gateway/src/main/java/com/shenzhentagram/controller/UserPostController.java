package com.shenzhentagram.controller;

import com.shenzhentagram.model.PostPage;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Meranote on 4/3/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserPostController extends TemplateRestController {

    public UserPostController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "post");
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PostPage> getPosts(
            Pageable pageable,
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/users/{id}/posts", pageable, PostPage.class, id);
    }

}

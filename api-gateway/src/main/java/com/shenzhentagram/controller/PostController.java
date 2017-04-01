package com.shenzhentagram.controller;

import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.model.Post;
import com.shenzhentagram.model.PostCreateDetail;
import com.shenzhentagram.model.PostUpdateDetail;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/posts")
public class PostController extends TemplateRestController {

    public PostController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "post");
    }

    @GetMapping()
    public Page<Post> getPosts(Pageable pageable) {
        return requestWithAuth(HttpMethod.GET, "/posts", pageable, Page.class);
    }

    @GetMapping(path = "/{id}")
    public Post getPosts(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/posts/{id}", Post.class, id);
    }

    @PostMapping()
    public Post createPost(HttpServletRequest request) throws IOException {
        PostCreateDetail detail = extractBody(request, PostCreateDetail.class);
        detail.setUser_id(((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()).getId());

        return requestWithAuth(HttpMethod.POST, "/posts", detail, Post.class);
    }

    @PatchMapping(path = "/{id}")
    public Post updatePost(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) throws IOException {
        return requestWithAuth(HttpMethod.PATCH, "/posts/{id}", extractBody(request, PostUpdateDetail.class), Post.class, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePost(
            @PathVariable("id") long id
    ) {
        requestWithAuth(HttpMethod.DELETE, "/posts/{id}", Void.class, id);
    }

}

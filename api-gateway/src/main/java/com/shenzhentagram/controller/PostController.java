package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/posts")
public class PostController extends TemplateRestController {

    @Autowired
    private UserController userController;

    public PostController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "post");
    }

    @GetMapping()
    public ResponseEntity<PostPage> getPosts(
            Pageable pageable
    ) throws IOException {
        ResponseEntity<PostPage> responseEntity = request(HttpMethod.GET, "/posts", pageable, PostPage.class);

        HashMap<Integer, User> cachedUsers = new HashMap<>();

        for(Post post : responseEntity.getBody().getContent()) {
            if(!cachedUsers.containsKey(post.getUserId())) {
                cachedUsers.put(post.getUserId(), userController.getUser(post.getUserId()).getBody());
            }

            post.setUser(cachedUsers.get(post.getUserId()));
        }

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPosts(
            @PathVariable("id") long id
    ) throws IOException {
        ResponseEntity<Post> responseEntity = request(HttpMethod.GET, "/posts/{id}", Post.class, id);
        responseEntity.getBody().setUser(userController.getUser(responseEntity.getBody().getUserId()).getBody());
        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(HttpServletRequest request) throws IOException {
        PostCreate detail = extractBody(request, PostCreate.class);
//        detail.set(getAuthenticatedUser().getId());

        // Create post
        ResponseEntity<Post> response = requestWithAuth(HttpMethod.POST, "/posts", detail, Post.class);

        // Increase post count
        userController.increasePosts((int) getAuthenticatedUser().getId());

        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) throws IOException {
        return requestWithAuth(HttpMethod.PATCH, "/posts/{id}", extractBody(request, PostUpdateDetail.class), Post.class, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("id") long id
    ) throws IOException {
        // Delete post
        ResponseEntity<Void> response = requestWithAuth(HttpMethod.DELETE, "/posts/{id}", Void.class, id);

        // Decrement post count
        userController.decreasePosts((int) getAuthenticatedUser().getId());

        return response;
    }

    /**
     * [Internal only] Increase post comment count by one
     */
    public ResponseEntity<Post> increaseComments(long id) throws IOException {
        return request(HttpMethod.POST, "/posts/{id}/comments/count", Post.class, id);
    }

    /**
     * [Internal only] Increase post reaction count by one
     */
    public ResponseEntity<Post> increaseReactions(long id) throws IOException {
        return request(HttpMethod.POST, "/posts/{id}/reactions/count", Post.class, id);
    }

    /**
     * [Internal only] Decrease post comment count by one
     */
    public ResponseEntity<Post> decreaseComments(long id) throws IOException {
        return request(HttpMethod.PUT, "/posts/{id}/comments/count", Post.class, id);
    }

    /**
     * [Internal only] Decrease post reaction count by one
     */
    public ResponseEntity<Post> decreaseReactions(long id) throws IOException {
        return request(HttpMethod.PUT, "/posts/{id}/reactions/count", Post.class, id);
    }

}

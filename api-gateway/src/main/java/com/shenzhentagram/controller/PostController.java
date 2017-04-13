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

        // Embed user into posts
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

        // Embed user into post
        responseEntity.getBody().setUser(userController.getUser(responseEntity.getBody().getUserId()).getBody());

        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(
            @RequestBody PostCreate detail
    ) throws IOException {
        detail.setUser_id(getAuthenticatedUser().getId());

        // Create post
        ResponseEntity<Post> response = request(HttpMethod.POST, "/posts", detail, Post.class);

        // Increase user post count
        // FIXME catch the exception (by now just ignored)
        try {
            userController.increasePosts((int) getAuthenticatedUser().getId());
        } catch(Exception ignored) {}

        // Embed user into post
        response.getBody().setUser(userController.getUser(response.getBody().getUserId()).getBody());

        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable("id") long id,
            @RequestBody PostUpdateDetail detail
    ) throws IOException {
        return request(HttpMethod.PATCH, "/posts/{id}", detail, Post.class, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("id") long id
    ) throws IOException {
        // FIXME check authenticated user before delete or send auth user id to post service and let it handle itself

        // Delete post
        ResponseEntity<Void> response = request(HttpMethod.DELETE, "/posts/{id}", Void.class, id);

        // Decrease user post count
        // FIXME catch the exception (by now just ignored)
        try {
            userController.decreasePosts((int) getAuthenticatedUser().getId());
        } catch(Exception ignored) {}

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

package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Meranote on 4/17/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CommentController extends TemplateRestController {

    @Autowired
    private UserController userController;

    public CommentController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "comment");
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity<CommentList> getCommentOfPostId(
            @PathVariable("post_id") int post_id
    ) {
        ResponseEntity<CommentList> responseEntity = request(HttpMethod.GET, "/posts/{post_id}/comments", CommentList.class, post_id);

        // Embed user into comments
        HashMap<Integer, User> cachedUsers = new HashMap<>();
        for(Comment comment : responseEntity.getBody()) {
            if(!cachedUsers.containsKey(comment.getUserId())) {
                cachedUsers.put(comment.getUserId(), userController.getUser(comment.getUserId()).getBody());
            }

            comment.setUser(cachedUsers.get(comment.getUserId()));
        }

        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<Void> createComment(
            @PathVariable("post_id") int post_id,
            @RequestBody CommentCreate comment
    ) {
        ResponseEntity<HashMap> responseEntity = request(HttpMethod.POST, "/posts/{post_id}/comments?userId=" + getAuthenticatedUser().getId(), comment, HashMap.class, post_id);
        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

}

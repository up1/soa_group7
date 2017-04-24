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
        // FIXME Create notification

        ResponseEntity<HashMap> responseEntity = request(HttpMethod.POST, "/posts/{post_id}/comments?userId=" + getAuthenticatedUser().getId(), comment, HashMap.class, post_id);
        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Void> updateComment(
            @PathVariable("post_id") int post_id,
            @PathVariable("comment_id") String comment_id,
            @RequestBody CommentUpdate comment
    ) {
        // FIXME check authenticated user before edit or send auth user id to comment service and let it handle itself

        ResponseEntity<HashMap> responseEntity = request(HttpMethod.PUT, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), comment, HashMap.class, post_id, comment_id);
        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("post_id") int post_id,
            @PathVariable("comment_id") String comment_id
    ) {
        // FIXME check authenticated user before delete or send auth user id to comment service and let it handle itself

        ResponseEntity<HashMap> responseEntity = request(HttpMethod.DELETE, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), HashMap.class, post_id, comment_id);
        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

}

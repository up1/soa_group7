package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Meranote on 4/17/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CommentController extends TemplateRestController {

    @Autowired
    private PostController postController;

    @Autowired
    private UserController userController;

    @Autowired
    private NotificationController notificationController;

    public CommentController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "comment");
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity<CommentList> getCommentOfPostId(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size,
            @PathVariable("post_id") int post_id
    ) {
        String url = "/posts/{post_id}/comments";
        if(page != null || size != null) {
            url += "?";
            url += page != null ? "offset=" + page : "";
            url += page != null && size != null ? "&" : "";
            url += size != null ? "limit=" + size : "";
        }

        ResponseEntity<CommentList> responseEntity = request(HttpMethod.GET, url, CommentList.class, post_id);

        // Embed user into comments
        try {
            HashMap<Integer, User> cachedUsers = new HashMap<>();
            for(Comment comment : responseEntity.getBody().getComments()) {
                if(!cachedUsers.containsKey(comment.getUserId())) {
                    cachedUsers.put(comment.getUserId(), userController.getUser(comment.getUserId()).getBody());
                }

                comment.setUser(cachedUsers.get(comment.getUserId()));
            }
        } catch(Exception ignored) {
            log.warn("Failed to map user into comment of post => " + post_id, ignored);
        }

        return responseEntity;
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Comment> getComment(
            @PathVariable("post_id") int post_id,
            @PathVariable("comment_id") String comment_id
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.GET, "/posts/{post_id}/comments/{comment_id}", Comment.class, post_id, comment_id);

        // Embed user into comments
        try {
            Comment comment = responseEntity.getBody();
            comment.setUser(userController.getUser(comment.getUserId()).getBody());
        } catch(Exception ignored) {
            log.warn("Failed to map user into comment => " + comment_id, ignored);
        }

        return responseEntity;
    }

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<Comment> createComment(
            @PathVariable("post_id") int post_id,
            @RequestBody CommentCreate commentCreate
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.POST, "/posts/{post_id}/comments?userId=" + getAuthenticatedUser().getId(), commentCreate, Comment.class, post_id);

        // Embed user into comments
        Comment comment = responseEntity.getBody();
        try {
            comment.setUser(userController.getUser(comment.getUserId()).getBody());
        } catch(Exception ignored) {
            log.warn("Failed to map user into comment => " + comment.getId(), ignored);
        }

        // Increase post comment count
        try {
            postController.increaseComments(post_id);
        } catch(Exception ignored) {
            log.warn("Increase post '" + post_id + "' comment count", ignored);
        }

        // Create comment notification
        try {
            notificationController.createCommentNotification(Math.toIntExact(getAuthenticatedUser().getId()), post_id, "abc");
        } catch(Exception ignored) {
            log.warn("Create notification failed", ignored);
        }

        return responseEntity;
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("post_id") int post_id,
            @PathVariable("comment_id") String comment_id,
            @RequestBody CommentUpdate commentUpdate
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.PUT, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), commentUpdate, Comment.class, post_id, comment_id);

        // Embed user into comments
        try {
            Comment comment = responseEntity.getBody();
            comment.setUser(userController.getUser(comment.getUserId()).getBody());
        } catch(Exception ignored) {
            log.warn("Failed to map user into comment => " + comment_id, ignored);
        }

        return responseEntity;
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("post_id") int post_id,
            @PathVariable("comment_id") String comment_id
    ) throws IOException {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.DELETE, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), Comment.class, post_id, comment_id);

        // Decrease post comment count
        try {
            postController.decreaseComments(post_id);
        } catch(Exception ignored) {
            log.warn("Decrease post '" + post_id + "' comment count", ignored);
        }

        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

    /**
     * Internal Only
     */
    public ResponseEntity<Void> deleteCommentsOfPostId(
            int post_id
    ) {
        return request(HttpMethod.DELETE, "/posts/{post_id}/comments" + getAuthenticatedUser().getId(), Void.class, post_id);
    }

}

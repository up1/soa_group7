package com.shenzhentagram.controller;

import com.shenzhentagram.model.Comment;
import com.shenzhentagram.model.CommentCreate;
import com.shenzhentagram.model.CommentList;
import com.shenzhentagram.model.CommentUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
            @PathVariable("post_id") int postId
    ) {
        String url = "/posts/{post_id}/comments";
        if(page != null || size != null) {
            url += "?";
            url += page != null ? "offset=" + page : "";
            url += page != null && size != null ? "&" : "";
            url += size != null ? "limit=" + size : "";
        }

        ResponseEntity<CommentList> responseEntity = request(HttpMethod.GET, url, CommentList.class, postId);

        userController.embeddedMultipleComment(responseEntity.getBody().getComments());

        return responseEntity;
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Comment> getComment(
            @PathVariable("post_id") int postId,
            @PathVariable("comment_id") String commentId
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.GET, "/posts/{post_id}/comments/{comment_id}", Comment.class, postId, commentId);

        userController.embeddedSingleComment(responseEntity.getBody());

        return responseEntity;
    }

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<Comment> createComment(
            @PathVariable("post_id") int postId,
            @RequestBody CommentCreate commentCreate
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.POST, "/posts/{post_id}/comments?userId=" + getAuthenticatedUser().getId(), commentCreate, Comment.class, postId);

        userController.embeddedSingleComment(responseEntity.getBody());

        postController.increaseComments(postId);

        notificationController.createCommentNotification(Math.toIntExact(getAuthenticatedUser().getId()), postId, responseEntity.getBody().getId());

        return responseEntity;
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("post_id") int postId,
            @PathVariable("comment_id") String commentId,
            @RequestBody CommentUpdate commentUpdate
    ) {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.PUT, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), commentUpdate, Comment.class, postId, commentId);

        userController.embeddedSingleComment(responseEntity.getBody());

        return responseEntity;
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("post_id") int postId,
            @PathVariable("comment_id") String commentId
    ) throws IOException {
        ResponseEntity<Comment> responseEntity = request(HttpMethod.DELETE, "/posts/{post_id}/comments/{comment_id}?userId=" + getAuthenticatedUser().getId(), Comment.class, postId, commentId);

        try {
            postController.decreaseComments(postId);
        } catch(Exception ignored) {
            log.warn("Decrease post '" + postId + "' comment count", ignored);
        }

        return new ResponseEntity<>(responseEntity.getStatusCode());
    }

    public void deleteCommentsOfPostId(
            int postId
    ) {
        guardRequester(() -> request(HttpMethod.DELETE, "/posts/{post_id}/comments" + getAuthenticatedUser().getId(), Void.class, postId));
    }

}

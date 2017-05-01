package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin
@RestController
@RequestMapping(path = "/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PostController extends TemplateRestController {

    @Autowired
    private UserController userController;

    @Autowired
    private CommentController commentController;

    public PostController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "post");
    }

    @GetMapping()
    @ApiOperation(
            tags = "Post-API",
            value = "getPosts",
            nickname = "getPosts",
            notes = "Get all posts (Timeline)"
    )
    public ResponseEntity<PostPage> getPosts(
            Pageable pageable
    ) {
        ResponseEntity<PostPage> responseEntity = request(HttpMethod.GET, "/posts", pageable, PostPage.class);

        userController.embeddedMultiplePost(responseEntity.getBody().getContent());

        return responseEntity;
    }

    @GetMapping("/{post_id}")
    @ApiOperation(
            tags = "Post-API",
            value = "getPost",
            nickname = "getPost",
            notes = "Get post detail by ID"
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<Post> getPost(
            @PathVariable("post_id") long postId
    ) {
        ResponseEntity<Post> responseEntity = request(HttpMethod.GET, "/posts/{post_id}", Post.class, postId);

        userController.embeddedSinglePost(responseEntity.getBody());

        return responseEntity;
    }

    @PostMapping()
    @ApiOperation(
            tags = "Post-API",
            value = "createPost",
            nickname = "createPost",
            notes = "Create new post"
    )
    public ResponseEntity<Post> createPost(
            @RequestBody PostCreate detail
    ) {
        detail.setUserId(getAuthenticatedUser().getId());

        ResponseEntity<Post> responseEntity = request(HttpMethod.POST, "/posts", detail, Post.class);

        userController.increasePosts((int) getAuthenticatedUser().getId());

        userController.embeddedSinglePost(responseEntity.getBody());

        return responseEntity;
    }

    @PatchMapping("/{post_id}")
    @ApiOperation(
            tags = "Post-API",
            value = "updatePost",
            nickname = "updatePost",
            notes = "Update post"
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<Post> updatePost(
            @PathVariable("id") long postId,
            @RequestBody PostUpdate detail
    ) {
        detail.setUserId(getAuthenticatedUser().getId());
        return request(HttpMethod.PATCH, "/posts/{id}", detail, Post.class, postId);
    }

    @DeleteMapping("/{post_id}")
    @ApiOperation(
            tags = "Post-API",
            value = "deletePost",
            nickname = "deletePost",
            notes = "Delete post"
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<Void> deletePost(
            @PathVariable("id") long postId
    ) {
        PostDelete detail = new PostDelete();
        detail.setUserId(getAuthenticatedUser().getId());
        ResponseEntity<Void> response = request(HttpMethod.DELETE, "/posts/{post_id}", detail, Void.class, postId);

        userController.decreasePosts((int) getAuthenticatedUser().getId());

        commentController.deleteCommentsOfPostId(Math.toIntExact(postId));

        return response;
    }

    public int increaseComments(long postId) {
        AtomicInteger commentCount = new AtomicInteger(-1);
        guardRequester(() -> commentCount.set(request(HttpMethod.POST, "/posts/{post_id}/comments/count", Post.class, postId).getBody().getCommentCount()));
        return commentCount.get();
    }

    public int increaseReactions(long postId) {
        AtomicInteger reactionCount = new AtomicInteger(-1);
        guardRequester(() -> reactionCount.set(request(HttpMethod.POST, "/posts/{post_id}/reactions/count", Post.class, postId).getBody().getReactions()));
        return reactionCount.get();
    }

    public int decreaseComments(long postId) {
        AtomicInteger commentCount = new AtomicInteger(-1);
        guardRequester(() -> commentCount.set(request(HttpMethod.PUT, "/posts/{post_id}/comments/count", Post.class, postId).getBody().getCommentCount()));
        return commentCount.get();
    }

    public int decreaseReactions(long postId) {
        AtomicInteger reactionCount = new AtomicInteger(-1);
        guardRequester(() -> reactionCount.set(request(HttpMethod.PUT, "/posts/{post_id}/reactions/count", Post.class, postId).getBody().getReactions()));
        return reactionCount.get();
    }

}

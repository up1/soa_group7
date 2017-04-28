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

import java.util.HashMap;
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

        // Embed user into posts
        userController.embeddedMultiplePost(responseEntity.getBody().getContent());

        return responseEntity;
    }

    @GetMapping("/{id}")
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
            @PathVariable("id") long id
    ) {
        ResponseEntity<Post> responseEntity = request(HttpMethod.GET, "/posts/{id}", Post.class, id);

        // Embed user into post
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
        detail.setUser_id(getAuthenticatedUser().getId());

        // Create post
        ResponseEntity<Post> responseEntity = request(HttpMethod.POST, "/posts", detail, Post.class);

        // Increase user post count
        userController.increasePosts((int) getAuthenticatedUser().getId());

        // Embed user into post
        userController.embeddedSinglePost(responseEntity.getBody());

        return responseEntity;
    }

    @PatchMapping("/{id}")
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
            @PathVariable("id") long id,
            @RequestBody PostUpdate detail
    ) {
        detail.setUser_id(getAuthenticatedUser().getId());
        return request(HttpMethod.PATCH, "/posts/{id}", detail, Post.class, id);
    }

    @DeleteMapping("/{id}")
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
            @PathVariable("id") long id
    ) {
        // Delete post
        ResponseEntity<Void> responseEntity = request(HttpMethod.DELETE, "/posts/{id}", Void.class, id);

        // Decrease user post count
        userController.decreasePosts((int) getAuthenticatedUser().getId());

        // Remove all post's comments
        commentController.deleteCommentsOfPostId(Math.toIntExact(id));

        return responseEntity;
    }

    /**
     * [Internal only] Increase post comment count by one
     */
    public int increaseComments(long id) {
        AtomicInteger commentCount = new AtomicInteger(-1);
        guardRequester(() -> {
            commentCount.set(request(HttpMethod.POST, "/posts/{id}/comments/count", Post.class, id).getBody().getComments());
        });
        return commentCount.get();
    }

    /**
     * [Internal only] Increase post reaction count by one
     */
    public int increaseReactions(long id) {
        AtomicInteger reactionCount = new AtomicInteger(-1);
        guardRequester(() -> {
            reactionCount.set(request(HttpMethod.POST, "/posts/{id}/reactions/count", Post.class, id).getBody().getReactions());
        });
        return reactionCount.get();
    }

    /**
     * [Internal only] Decrease post comment count by one
     */
    public int decreaseComments(long id) {
        AtomicInteger commentCount = new AtomicInteger(-1);
        guardRequester(() -> {
            commentCount.set(request(HttpMethod.PUT, "/posts/{id}/comments/count", Post.class, id).getBody().getComments());
        });
        return commentCount.get();
    }

    /**
     * [Internal only] Decrease post reaction count by one
     */
    public int decreaseReactions(long id) {
        AtomicInteger reactionCount = new AtomicInteger(-1);
        guardRequester(() -> {
            reactionCount.set(request(HttpMethod.PUT, "/posts/{id}/reactions/count", Post.class, id).getBody().getReactions());
        });
        return reactionCount.get();
    }

}

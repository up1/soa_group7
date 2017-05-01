package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin
@RestController
@RequestMapping(path = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController extends TemplateRestController {

    public UserController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "user");
    }

    @GetMapping("/{id}")
    @ApiOperation(
            tags = "User-API",
            value = "getUser",
            nickname = "getUser",
            notes = "Get user detail by user ID"
    )
    @ApiResponses({
            @ApiResponse(code = 404, message = "User not found")
    })
    public ResponseEntity<User> getUser(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/users/{id}", User.class, id);
    }

    @GetMapping("/search")
    @ApiOperation(
            tags = "User-API",
            value = "searchUser",
            nickname = "searchUser",
            notes = "Search user by name"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matched user's full_name or display_name")
    })
    public ResponseEntity<UserList> searchUser(
            @RequestParam("name") String name
    ) {
        return request(HttpMethod.GET, "/users/search?name=" + name, UserList.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            tags = "User-API",
            value = "createUser",
            nickname = "createUser",
            notes = "Create a new user (Register)"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "New user created")
    })
    public ResponseEntity<User> createUser(
            @ApiParam("Register detail") @RequestBody UserRegister detail
    ) {
        return request(HttpMethod.POST, "/users", detail, User.class);
    }

    @GetMapping("/self")
    @ApiOperation(
            tags = "User-API",
            value = "getSelfUser",
            nickname = "getSelfUser",
            notes = "Get current authenticated user detail"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Auth token", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @ApiResponses({
            @ApiResponse(code = 401, message = "Not authenticated (no token)")
    })
    public ResponseEntity<User> getSelf() {
        return getUser(getAuthenticatedUser().getId());
    }

    @PatchMapping(path = "/self")
    @ApiOperation(
            tags = "User-API",
            value = "updateProfile",
            nickname = "updateProfile",
            notes = "Update profile to current authenticated user"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Auth token", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated user detail"),
            @ApiResponse(code = 401, message = "Not authenticated (no token)")
    })
    public ResponseEntity<User> updateSelf(
            @ApiParam("Update detail") @RequestBody UserUpdate detail
    ) {
        return request(HttpMethod.PATCH, "/users/{id}", detail, User.class, getAuthenticatedUser().getId());
    }

    @PatchMapping(path = "/self/picture")
    @ApiOperation(
            tags = "User-API",
            value = "updateProfilePicture",
            nickname = "updateProfilePicture",
            notes = "Update profile picture to current authenticated user"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Auth token", required = true, dataType = "string", paramType = "header", defaultValue = "Bearer ")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated user profile picture"),
            @ApiResponse(code = 401, message = "Not authenticated (no token)")
    })
    public ResponseEntity<User> updateSelfPicture(
            @ApiParam("Update detail") @RequestBody UserUpdatePicture detail
    ) {
        return request(HttpMethod.PATCH, "/users/{id}/picture", detail, User.class, getAuthenticatedUser().getId());
    }

    /**
     * [Internal only] Increase user posts count by one
     */
    public int increasePosts(long id) {
        AtomicInteger postCount = new AtomicInteger(-1);
        guardRequester(() -> postCount.set((int) request(HttpMethod.POST, "/users/{id}/posts/count", HashMap.class, id).getBody().get("post_count")));
        return postCount.get();
    }

    /**
     * [Internal only] Decrease user posts count by one
     */
    public int decreasePosts(long id) {
        AtomicInteger postCount = new AtomicInteger(-1);
        guardRequester(() -> postCount.set((int) request(HttpMethod.PUT, "/users/{id}/posts/count", HashMap.class, id).getBody().get("post_count")));
        return postCount.get();
    }

    /**
     * [Internal only] Increase user following count by one
     */
    public void increaseFollowing(long id) {
        guardRequester(() -> request(HttpMethod.POST, "/users/{id}/follows", Void.class, id));
    }

    /**
     * [Internal only] Decrease user following count by one
     */
    public void decreaseFollowing(long id) {
        guardRequester(() ->request(HttpMethod.PUT, "/users/{id}/follows", Void.class, id));
    }

    /**
     * [Internal only] Increase user follower count by one
     */
    public void increaseFollower(long id) {
        guardRequester(() -> request(HttpMethod.POST, "/users/{id}/followed_by", Void.class, id));
    }

    /**
     * [Internal only] Decrease user follower count by one
     */
    public void decreaseFollower(long id) {
        guardRequester(() ->request(HttpMethod.PUT, "/users/{id}/followed_by", Void.class, id));
    }

    /**
     * [Internal only] Convert follower user id lists to user detail lists
     * @param userIds
     * @return {@link Follower}
     */
    public Follower convertFollowerIds(List<Integer> userIds) {
        return new Follower() {{
            setFollower(convertFollowsIds(userIds));
        }};
    }

    /**
     * [Internal only] Convert following user id lists to user detail lists
     * @param userIds
     * @return {@link Following }
     */
    public Following convertFollowingIds(List<Integer> userIds) {
        return new Following() {{
            setFollowing(convertFollowsIds(userIds));
        }};
    }

    /**
     * Convert follower/following user id list to user detail lists
     */
    private List<User> convertFollowsIds(List<Integer> userIds) {
        List<User> users = new ArrayList<>();

        guardRequester(() -> {
            HashMap<Integer, User> cachedUsers = new HashMap<>();
            for(Integer id : userIds) {
                if(!cachedUsers.containsKey(id)) {
                    cachedUsers.put(id, getUser(id).getBody());
                }

                users.add(cachedUsers.get(id));
            }
        });

        return users;
    }

    /**
     * [Internal only] Embedded user into multiple post
     * @param posts
     */
    public void embeddedMultiplePost(List<Post> posts) {
        guardRequester(() -> {
            HashMap<Integer, User> cachedUsers = new HashMap<>();
            for(Post post : posts) {
                if(!cachedUsers.containsKey(post.getUserId())) {
                    cachedUsers.put(post.getUserId(), getUser(post.getUserId()).getBody());
                }

                post.setUser(cachedUsers.get(post.getUserId()));
            }
        });
    }

    /**
     * [Internal only] Embedded user into single post<br>
     * <b>
     *     Don't use this method if you want to embed multiple post<br>
     *     See {@link UserController#embeddedMultiplePost(List)} instead
     * </b>
     * @param post
     */
    public void embeddedSinglePost(Post post) {
        guardRequester(() -> post.setUser(getUser(post.getUserId()).getBody()));
    }

    /**
     * [Internal only] Embedded user into multiple comment
     * @param comments
     */
    public void embeddedMultipleComment(List<Comment> comments) {
        guardRequester(() -> {
            HashMap<Integer, User> cachedUsers = new HashMap<>();
            for(Comment comment : comments) {
                if(!cachedUsers.containsKey(comment.getUserId())) {
                    cachedUsers.put(comment.getUserId(), getUser(comment.getUserId()).getBody());
                }

                comment.setUser(cachedUsers.get(comment.getUserId()));
            }
        });
    }

    /**
     * [Internal only] Embedded user into single comment<br>
     * <b>
     *     Don't use this method if you want to embed multiple comment<br>
     *     See {@link UserController#embeddedMultipleComment(List)} instead
     * </b>
     * @param comment
     */
    public void embeddedSingleComment(Comment comment) {
        guardRequester(() -> comment.setUser(getUser(comment.getUserId()).getBody()));
    }

    /**
     * [Internal only] Embedded user into multiple reactions
     * @param reactions
     */
    public void embeddedMultipleReaction(List<Reaction> reactions) {
        guardRequester(() -> {
            HashMap<Long, User> cachedUsers = new HashMap<>();
            for(Reaction reaction : reactions) {
                if(!cachedUsers.containsKey(reaction.getUserId())) {
                    cachedUsers.put(reaction.getUserId(), getUser(reaction.getUserId()).getBody());
                }

                reaction.setUser(cachedUsers.get(reaction.getUserId()));
            }
        });
    }

    /**
     * [Internal only] Embedded user into single reaction<br>
     * <b>
     *     Don't use this method if you want to embed multiple reaction<br>
     *     See {@link UserController#embeddedMultipleReaction(List)} instead
     * </b>
     * @param reaction
     */
    public void embeddedSingleReaction(Reaction reaction) {
        guardRequester(() -> reaction.setUser(getUser(reaction.getUserId()).getBody()));
    }

}

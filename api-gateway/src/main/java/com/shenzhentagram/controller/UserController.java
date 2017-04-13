package com.shenzhentagram.controller;

import com.shenzhentagram.model.*;
import io.swagger.annotations.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Meranote on 3/20/2017.
 */
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
    ) throws IOException {
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
    ) throws IOException {
        return request(HttpMethod.GET, "/users/search?name" + name, UserList.class);
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
    public ResponseEntity<Void> createUser(
            @ApiParam("Register detail") @RequestBody UserRegister detail
    ) throws IOException {
        return request(HttpMethod.POST, "/users", detail, Void.class);
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
    public ResponseEntity<User> getSelf() throws IOException {
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
    ) throws IOException {
        return request(HttpMethod.PATCH, "/users/{id}", detail, User.class, getAuthenticatedUser().getId());
    }

    /**
     * [Internal only] Increase user posts count by one
     */
    public int increasePosts(long id) throws IOException {
        return (int) request(HttpMethod.POST, "/users/{id}/posts/count", HashMap.class, id).getBody().get("post_count");
    }

    /**
     * [Internal only] Decrease user posts count by one
     */
    public int decreasePosts(long id) throws IOException {
        return (int) request(HttpMethod.PUT, "/users/{id}/posts/count", HashMap.class, id).getBody().get("post_count");
    }

}

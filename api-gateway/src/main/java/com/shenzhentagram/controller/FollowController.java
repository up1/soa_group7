package com.shenzhentagram.controller;

import com.shenzhentagram.model.FollowIds;
import com.shenzhentagram.model.Follower;
import com.shenzhentagram.model.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Meranote on 4/27/2017.
 */
@RestController
@CrossOrigin
@RequestMapping("/users")
public class FollowController extends TemplateRestController {

    @Autowired
    private UserController userController;

    /**
     * Setup the template for REST request
     *
     * @param environment
     * @param restTemplateBuilder
     */
    public FollowController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "follow");
    }

    @GetMapping("/{id}/follower")
    public ResponseEntity<Follower> getFollower(
            @PathVariable("id") int userId
    ) {
        ResponseEntity<FollowIds> responseEntity = request(HttpMethod.GET, "/users/{id}/follows", FollowIds.class, userId);

        // Convert ids to users
        Follower follower = userController.convertFollowerIds(responseEntity.getBody().getFollower());

        return new ResponseEntity<>(follower, responseEntity.getStatusCode());
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<Following> getFollowing(
            @PathVariable("id") int userId
    ) {
        ResponseEntity<FollowIds> responseEntity = request(HttpMethod.GET, "/users/{id}/follows", FollowIds.class, userId);

        // Convert ids to users
        Following following = userController.convertFollowingIds(responseEntity.getBody().getFollowing());

        return new ResponseEntity<>(following, responseEntity.getStatusCode());
    }

    @PostMapping("/{id}/follows")
    public ResponseEntity<Void> followTo(
            @PathVariable("id") int userId
    ) {
        HashMap<String, Object> sendPayload = new HashMap<String, Object>() {{
            put("userId", getAuthenticatedUser().getId());
        }};

        return request(HttpMethod.POST, "/users/{id}/follows", sendPayload, Void.class, userId);
    }

    @DeleteMapping("/{id}/follows")
    public ResponseEntity<Void> unfollowTo(
            @PathVariable("id") int userId
    ) {
        HashMap<String, Object> sendPayload = new HashMap<String, Object>() {{
            put("userId", getAuthenticatedUser().getId());
        }};

        return request(HttpMethod.DELETE, "/users/{id}/follows", sendPayload, Void.class, userId);
    }

}

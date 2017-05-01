package com.shenzhentagram.controller;

import com.shenzhentagram.model.Reaction;
import com.shenzhentagram.model.ReactionBase;
import com.shenzhentagram.model.ReactionList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Meranote on 4/27/2017.
 */
@RestController
@CrossOrigin
@RequestMapping("/posts")
public class ReactionController extends TemplateRestController {

    private static final String BASE_URL = "/posts/{post_id}/reactions";
    
    /**
     * Setup the template for REST request
     *
     * @param environment
     * @param restTemplateBuilder
     */
    public ReactionController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "reaction");
    }

    @GetMapping("/{post_id}/reactions")
    public ResponseEntity<ReactionList> getReactions(
            @PathVariable("post_id") int postId
    ) {
        return request(HttpMethod.GET, BASE_URL, ReactionList.class, postId);
    }

    @PostMapping("/{post_id}/reactions")
    public ResponseEntity<Reaction> createReaction(
            @PathVariable("post_id") int postId,
            @RequestBody ReactionBase reaction
    ) {
        reaction.setUserId(getAuthenticatedUser().getId());
        return request(HttpMethod.POST, BASE_URL, reaction, Reaction.class, postId);
    }

    @PutMapping("/{post_id}/reactions")
    public ResponseEntity<Reaction> updateReaction(
            @PathVariable("post_id") int postId,
            @RequestBody ReactionBase reaction
    ) {
        reaction.setUserId(getAuthenticatedUser().getId());
        return request(HttpMethod.PUT, BASE_URL, reaction, Reaction.class, postId);
    }

    @DeleteMapping("/{post_id}/reactions")
    public ResponseEntity<Reaction> deleteReaction(
            @PathVariable("post_id") int postId
    ) {
        ReactionBase reaction = new ReactionBase();
        reaction.setUserId(getAuthenticatedUser().getId());

        return request(HttpMethod.DELETE, BASE_URL, reaction, Reaction.class, postId);
    }

}

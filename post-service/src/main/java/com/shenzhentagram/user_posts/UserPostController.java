package com.shenzhentagram.user_posts;

import com.shenzhentagram.model.AuthenticatedUser;
import com.shenzhentagram.posts.Post;
import com.shenzhentagram.posts.PostRepository;
import com.shenzhentagram.posts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by phompang on 3/6/2017 AD.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserPostController {

    private PostService postService;
    private PostRepository postRepository;

    @Autowired
    public UserPostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/self/posts")
    public Page<Post> getPosts(Authentication authentication, Pageable pageable) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        return postRepository.findAllByUserId(userDetails.getId().longValue(), pageable);
    }

    @GetMapping(value = "/{user_id}/posts")
    public Page<Post> getPosts(Pageable pageable,
                               @PathVariable("user_id") long user_id) {
        return postRepository.findAllByUserId(user_id, pageable);
    }
}

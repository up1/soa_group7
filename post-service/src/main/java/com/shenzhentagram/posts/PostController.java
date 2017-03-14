package com.shenzhentagram.posts;

import com.shenzhentagram.model.AuthenticatedUser;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by phompang on 3/5/2017 AD.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private PostService postService;
    private PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping()
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public Post getPosts(@PathVariable("id") long id) {
        return postRepository.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<?> postPost(Authentication authentication,
                                  @RequestParam(value = "caption") String caption,
                                  @RequestParam(value = "type") String type,
                                  @RequestParam(value = "file") MultipartFile file) throws XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String media = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();

        Post post = new Post();
        post.setCaption(caption);
        post.setType(type);
        post.setMedia(media);
        post.setUserId(userDetails.getId());

        return postService.storePost(post, file);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> patchPost(Authentication authentication,
                                       @PathVariable("id") long id,
                                       @RequestParam(value = "caption") String caption) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();

        Post post = postRepository.findOne(id);

        if (userDetails.getId() != post.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        post.setCaption(caption);
        postRepository.save(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePost(Authentication authentication,
                                       @PathVariable("id") long id) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();

        Post post = postRepository.findOne(id);

        if (userDetails.getId() != post.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        postRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

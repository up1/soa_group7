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

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public Page<Post> getPosts(Pageable pageable) {
        return postService.findAllByPage(pageable);
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

        Post post = postService.findById(id);

        if (userDetails.getId() != post.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        post.setCaption(caption);
        postService.patchPost(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePost(Authentication authentication,
                                       @PathVariable("id") long id) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();

        Post post = postService.findById(id);

        if (userDetails.getId() != post.getUserId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        postService.deletePost(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

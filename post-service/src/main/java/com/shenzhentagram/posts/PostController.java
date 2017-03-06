package com.shenzhentagram.posts;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> postPost(@RequestParam(value = "caption") String caption,
                                  @RequestParam(value = "type") String type,
                                  @RequestParam(value = "file") MultipartFile file) throws XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String media = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        Post post = new Post();
        post.setCaption(caption);
        post.setType(type);
        post.setMedia(media);

        return postService.storePost(post, file);
    }
}

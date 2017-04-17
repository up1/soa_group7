package com.shenzhentagram.posts;

import com.shenzhentagram.exception.UserIdNotMatchException;
import com.shenzhentagram.utility.FileUtility;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
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
        return postService.findPostOrFail(id);
    }

    @PostMapping()
    public ResponseEntity<Post> postPost(
            @RequestBody Map<String, Object> payload
    ) throws XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException, MagicParseException, MagicException, MagicMatchNotFoundException {
        String fileBase64 = (String) payload.remove("file");
        FileUtility.FileDetail file = FileUtility.extractFileFromBase64(fileBase64);

        String media = UUID.randomUUID().toString() + "." + file.extension;

        Post post = new Post();
        post.setCaption((String) payload.get("caption"));
        post.setType((String) payload.get("type"));
        post.setMedia(media);
        post.setUserId((int) payload.get("user_id"));

        return postService.storePost(post, file);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Post> patchPost(
                                       @PathVariable("id") long id,
                                       @RequestBody Map<String, Object> payload) {
        Post post = postService.findPostOrFail(id);

        String caption = (String) payload.get("caption");
        long user_id = ((Integer) payload.get("user_id")).longValue();

        if (user_id != post.getUserId()) {
            throw new UserIdNotMatchException(String.format("User ID %d not match with post's user ID", user_id));
        }

        post.setCaption(caption);
        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Post> deletePost(
                                       @PathVariable("id") long id,
                                       @RequestParam(value = "user_id") long user_id) {
        Post post = postService.findPostOrFail(id);

        if (user_id != post.getUserId()) {
            throw new UserIdNotMatchException(String.format("User ID %d not match with post's user ID", user_id));
        }

        postRepository.delete(id);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/comments/count")
    public ResponseEntity<Post> increaseComments(@PathVariable("id") long id) {
        Post post = postService.findPostOrFail(id);
        post.setComments(post.getComments()+1);
        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}/reactions/count")
    public ResponseEntity<Post> increaseReactions(@PathVariable("id") long id) {
        Post post = postService.findPostOrFail(id);
        post.setReactions(post.getReactions()+1);
        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/comments/count")
    public ResponseEntity<Post> decreaseComments(@PathVariable("id") long id) {
        Post post = postService.findPostOrFail(id);
        post.setComments(post.getComments()-1);
        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @PutMapping(value = "/{id}/reactions/count")
    public ResponseEntity<Post> decreaseReactions(@PathVariable("id") long id) {
        Post post = postService.findPostOrFail(id);
        post.setReactions(post.getReactions()-1);
        postRepository.save(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

}

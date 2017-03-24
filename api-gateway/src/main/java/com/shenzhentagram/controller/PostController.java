package com.shenzhentagram.controller;

import com.shenzhentagram.model.Post;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Meranote on 3/20/2017.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/posts")
public class PostController extends TemplateRestController {

    // FIXME Extract authorization token

    public PostController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "post");
    }

    @GetMapping()
    public Page<Post> getPosts(Pageable pageable) {
        return restTemplate.getForObject("/posts", Page.class);
    }

    @GetMapping(path = "/{id}")
    public Post getPosts(
            @PathVariable("id") long id
    ) {
        return restTemplate.getForObject("/posts/{id}", Post.class, id);
    }

    @PostMapping()
    public Post createPost(
            @RequestParam(value = "caption") String caption,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "file") MultipartFile file
    ) {
        // TODO Post (upload file and sent to service)
        throw new NotImplementedException();
    }

    @PatchMapping(path = "/{id}")
    public Post updatePost(
            @PathVariable("id") long id,
            @RequestParam(value = "caption") String caption
    ) {
        // FIXME Send JSON body (post service is accepting query params by now)
//        return restTemplate.patchForObject("/posts/{id}", new PostUpdateDetail(id, caption), Post.class, id);
        return restTemplate.patchForObject(String.format("/posts/{id}?id=%d&caption=%s", id, caption), null, Post.class, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePost(
            @PathVariable("id") long id
    ) {
        // QUESTION Did this need to return the deleted post?
        restTemplate.delete("/posts/{id}", id);
    }

}

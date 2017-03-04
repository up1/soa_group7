package com.shenzhentagram.posts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by phompang on 3/4/2017 AD.
 */
@RestController("/posts")
public class PostController {

    @GetMapping()
    public void getPosts() {

    }
}

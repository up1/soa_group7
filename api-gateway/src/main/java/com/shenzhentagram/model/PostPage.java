package com.shenzhentagram.model;

import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meranote on 4/3/2017.
 */
public class PostPage extends PageImpl<Post> {

    public PostPage() {
        super(new ArrayList<>());
    }

    public PostPage(List<Post> content) {
        super(content);
    }

}

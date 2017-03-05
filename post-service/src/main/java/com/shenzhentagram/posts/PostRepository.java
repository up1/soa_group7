package com.shenzhentagram.posts;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by phompang on 3/5/2017 AD.
 */
@RepositoryRestResource(collectionResourceRel = "post", path = "posts")
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findById(@Param("id") long id);
}

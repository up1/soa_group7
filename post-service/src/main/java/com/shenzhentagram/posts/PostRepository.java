package com.shenzhentagram.posts;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by phompang on 3/5/2017 AD.
 */
@RepositoryRestResource(collectionResourceRel = "post", path = "posts")
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}

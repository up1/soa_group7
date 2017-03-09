package com.shenzhentagram.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by phompang on 3/5/2017 AD.
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllByUserId(Long id, Pageable pageable);
}

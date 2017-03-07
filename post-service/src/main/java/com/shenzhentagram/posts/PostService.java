package com.shenzhentagram.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by phompang on 3/5/2017 AD.
 */
public interface PostService {
    Page<Post> findAllByPage(Pageable pageable);
    Page<Post> findAllByUserId(Long id, Pageable pageable);
    Post findById(Long id);
    void patchPost(Post post);
    void deletePost(Long id);
    ResponseEntity<Post> storePost(Post post, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException;
}

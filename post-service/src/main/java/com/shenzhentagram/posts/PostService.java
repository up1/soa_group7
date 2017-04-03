package com.shenzhentagram.posts;

import com.shenzhentagram.exception.PostNotFoundException;
import com.shenzhentagram.utility.FileUtility;
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
    ResponseEntity<Post> storePost(Post post, FileUtility.FileDetail file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException;
    Post findPostOrFail(long id) throws PostNotFoundException;
}

package com.shenzhentagram.posts;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by phompang on 3/5/2017 AD.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private MinioClient minioClient;

    @Value("${minio.url}")
    private String url;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void init() throws XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
            minioClient = new MinioClient(url, accessKey, secretKey);

            if (minioClient.bucketExists(bucket)) {
                System.out.println("Bucket already exists.");
            } else {
                minioClient.makeBucket(bucket);
            }
        } catch (MinioException e) {
            System.out.println("Error occur" + e);
        }
    }

    @Override
    public Page<Post> findAllByPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAllByUserId(Long id, Pageable pageable) {
        return postRepository.findAllByUserId(id, pageable);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public void patchPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(id);
    }

    @Override
    public ResponseEntity<?> storePost(Post post, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException {
        try {
            minioClient.putObject(bucket, post.getMedia(), file.getInputStream(), file.getSize(), file.getContentType());

            postRepository.save(post);
            System.out.println("Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

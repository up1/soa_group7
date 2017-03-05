package com.shenzhentagram.posts;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by phompang on 3/5/2017 AD.
 */
@CrossOrigin
@RestController
public class PostController {
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
    public PostController(PostRepository postRepository) {
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

    @PostMapping()
    public ResponseEntity<?> post(@RequestParam(value = "caption") String caption,
                                  @RequestParam(value = "type") String type,
                                  @RequestParam(value = "file") MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException, XmlPullParserException {
        try {
            String media = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            minioClient.putObject(bucket, media, file.getInputStream(), file.getSize(), file.getContentType());

            Post post = new Post();
            post.setCaption(caption);
            post.setType(type);
            post.setMedia(media);

            postRepository.save(post);
            System.out.println("Success");

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

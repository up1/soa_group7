package com.shenzhentagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.utility.FileUtility;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private static Log log = LogFactory.getLog(UserController.class);

    @Value("${minio.url}")
    private String url;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    private MinioClient minio;

    @PostConstruct
    public void postConstruction() throws XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
            minio = new MinioClient(url, accessKey, secretKey);

            if (minio.bucketExists(bucket)) {
                log.info("postConstruction() : Using bucket '" + bucket + "'");
            } else {
                log.info("postConstruction() : No name bucket exists, creating a new one");
                minio.makeBucket(bucket);
            }
        } catch (MinioException e) {
            log.error("postConstruction() : minio client error => " + e);
        }
    }

    @GetMapping("/{id}")
    public User getUser(
            @PathVariable("id") long id
    ) {
        return this.userRepository.findById(id);
    }

    @GetMapping("/search")
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return this.userRepository.findByName(name);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(
            @RequestBody Map<String, Object> payload
    ) throws Exception {
        // Extract the password
        String password = (String) payload.remove("password");

        // If have image, extract
        FileUtility.FileDetail fileDetail = null;
        if(payload.containsKey("profile_picture") && payload.get("profile_picture") != null) {
            // Extract image (base64)
            String imageBase64 = (String) payload.remove("profile_picture");
            fileDetail = FileUtility.extractFileFromBase64(imageBase64);
        }

        // Extract user
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(payload, User.class);

        try {
            // if have image, upload
            if (fileDetail != null) {
                // Set picture & upload to storage
                user.setProfile_picture(UUID.randomUUID().toString() + "." + fileDetail.extension);

                // Upload file
                minio.putObject(
                        bucket,
                        user.getProfile_picture(),
                        fileDetail.inputStream,
                        fileDetail.size,
                        fileDetail.type
                );
            }

            // Save user
            this.userRepository.save(user, password);

            // Return created user
            user = this.userRepository.findByUsername(user.getUsername());
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            if (fileDetail != null) {
                minio.removeObject(bucket, user.getProfile_picture());
            }

            throw e;
        }
    }

    @PatchMapping("/{id}")
    public User updateSelf(
            @PathVariable("id") long id,
            @RequestBody Map<String, Object> payload
    ) {
        User user = this.userRepository.findById(id);

        if(payload.containsKey("full_name")) {
            user.setFull_name((String) payload.get("full_name"));
        }

        if(payload.containsKey("bio")) {
            user.setBio((String) payload.get("bio"));
        }

        if(payload.containsKey("display_name")) {
            user.setDisplay_name((String) payload.get("display_name"));
        }

        this.userRepository.update(user);

        return user;
    }

    @PatchMapping("/{id}/picture")
    public User updateSelfProfilePicture(
            @PathVariable("id") long id,
            @RequestBody Map<String, Object> payload
    ) throws Exception {
        User user = this.userRepository.findById(id);

        if(!payload.containsKey("profile_picture")) {
            throw new Exception("no profile_picture field");
        }

        // Extract image (base64)
        String imageBase64 = (String) payload.remove("profile_picture");
        FileUtility.FileDetail fileDetail = FileUtility.extractFileFromBase64(imageBase64);

        try {
            // upload image
            // Set picture & upload to storage
            user.setProfile_picture(UUID.randomUUID().toString() + "." + fileDetail.extension);

            // Upload file
            minio.putObject(
                    bucket,
                    user.getProfile_picture(),
                    fileDetail.inputStream,
                    fileDetail.size,
                    fileDetail.type
            );

            // Save user
            this.userRepository.update(user);
            return user;
        } catch (MinioException e) {
            throw e;
        } catch (DataAccessException e) {
            minio.removeObject(bucket, user.getProfile_picture());
            throw e;
        }
    }

    @PostMapping("/{id}/posts/count")
    public void increasePosts(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setPost_count(user.getPost_count() + 1);
        this.userRepository.update(user);
    }

    @PutMapping("/{id}/posts/count")
    public void decreasePosts(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setPost_count(user.getPost_count() - 1);
        this.userRepository.update(user);
    }

    @PostMapping("/{id}/follows")
    public void increaseFollows(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setFollows(user.getFollows() + 1);
        this.userRepository.update(user);
    }

    @PutMapping("/{id}/follows")
    public void decreaseFollows(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setFollows(user.getFollows() - 1);
        this.userRepository.update(user);
    }

    @PostMapping("/{id}/followed_by")
    public void increaseFollowed_by(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setFollowed_by(user.getFollowed_by() + 1);
        this.userRepository.update(user);
    }

    @PutMapping("/{id}/followed_by")
    public void decreaseFollowed_by(
            @PathVariable("id") long id
    ) {
        User user = this.userRepository.findById(id);
        user.setFollowed_by(user.getFollowed_by() - 1);
        this.userRepository.update(user);
    }

}

package com.shenzhentagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.AuthenticatedUser;
import com.shenzhentagram.utility.FileUtility;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.xmlpull.v1.XmlPullParserException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    private static Log log = LogFactory.getLog(FileUtility.class);

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

    @GetMapping("/{user_id}")
    public User getUser(
            @PathVariable("user_id") long id
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
    public ResponseEntity<Void> createUser(
            @RequestBody Map<String, Object> payload
    ) throws Exception {
        // Extract the password
        String password = (String) payload.remove("password");

        // If have image, extract
        FileUtility.FileDetail fileDetail = null;
        if(payload.containsKey("profile_picture")) {
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
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MinioException e) {
            log.error("postConstruction() : minio client error => " + e);
        } catch (DataAccessException e) {
            minio.removeObject(bucket, user.getProfile_picture());
            log.trace(e);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/self")
    public User getSelf() {
        AuthenticatedUser auth = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findById(auth.getId());
    }

    @PatchMapping("/self")
    public void updateSelf() {
        // TODO Implement update self profile
        throw new NotImplementedException();
    }

    @PostMapping("/{id}/posts/count")
    public ResponseEntity<Map<String, Object>> increasePosts(
            @PathVariable("id") int id
    ) {
        // TODO Make a method in UserRepository that increase post_count by one
        throw new NotImplementedException();
    }

    @PutMapping("/{id}/posts/count")
    public ResponseEntity<Map<String, Object>> decreasePosts(
            @PathVariable("id") int id
    ) {
        // TODO Make a method in UserRepository that decrease post_count by one
        throw new NotImplementedException();
    }

}

package follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class FollowController {

    // For Annotation
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

    @Autowired
    private FollowByRepository followByRepository;
    @Autowired
    private FollowingRepository followingRepository;

    @RequestMapping(path = "/createdata", method = RequestMethod.GET)
    public void postCreateData() {
        List<User> followby = new ArrayList<User>();
        followby.add(new User(4));
        followby.add(new User(2));
        followby.add(new User(3));

        List<User> following = new ArrayList<User>();
        following.add(new User(4));
        following.add(new User(2));
        following.add(new User(3));

        followByRepository.save(new FollowBy(1, followby));
        followingRepository.save(new Following(1,following));

    }

    @GetMapping("/{id}/follows")
    public Following getFollows(@PathVariable("id") Long id) {

        Following following = followingRepository.findByUserId(id);
        return following;

    }

    @GetMapping("/{id}/followed_by")
    public FollowBy getFollowed_by    (@PathVariable("id") Long id) {

        FollowBy followby = followByRepository.findByUserId(id);
        return followby;

    }

    @PostMapping("/{id}/followed_by")
    public FollowBy createFollowed_by   (@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        List<User> users = new ArrayList<>();
        FollowBy followby;
        followby = followByRepository.findByUserId((int)payload.get("userId"));
        try {
            users = followby.getUsers();
            users.add(new User(id));
            followby.setUsers(users);
            followByRepository.save(followby);
        }catch (Exception e){
            users.add(new User(id));
            followby = new FollowBy((int)payload.get("userId") ,users);
            followByRepository.save(followby);
        }

        return followby;

    }

    @PostMapping("/{id}/follows")
    public Following createFollows   (@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        List<User> users = new ArrayList<>();
        Following following;
        following = followingRepository.findByUserId((int)payload.get("userId"));
        try {
            users = following.getUsers();
            users.add(new User(id));
            following.setUsers(users);
            followingRepository.save(following);
        }catch (Exception e){
            users.add(new User(id));
            following = new Following((int)payload.get("userId") ,users);
            followingRepository.save(following);
        }

        return following;

    }

}

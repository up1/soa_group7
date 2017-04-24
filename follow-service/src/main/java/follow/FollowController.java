package follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    private FollowsRepository followsRepository;

    @RequestMapping(path = "/createdata", method = RequestMethod.GET)
    public Follows postCreateData() {
        ArrayList<Integer> followby = new ArrayList<>();
        followby.add(4);
        followby.add(2);
        followby.add(3);

        ArrayList<Integer> following = new ArrayList<>();
        following.add(4);
        following.add(2);
        following.add(3);

//        followByRepository.save(new FollowBy(1, followby));
//        followingRepository.save(new Following(1,following));
        Follows follows = new Follows("1", followby, following);
        followsRepository.save(follows);
        return follows;

    }

    @GetMapping("/{id}/follows")
    public Follows getFollows(@PathVariable("id") String id) {

        Follows follows = followsRepository.findById(id);
        return follows;

    }

//    @GetMapping("/{id}/follows")
//    public FollowBy getFollowed_by    (@PathVariable("id") Long id) {
//
//        FollowBy followby = followByRepository.findByUserId(id);
//        return followby;
//
//    }

    @PostMapping("/{id}/follower")
    public Follows createFollower   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(id);
        try {
            follower = follows.getFollower();
            Set<Integer> mySet = new HashSet<Integer>(follower);
            mySet.add((int)payload.get("userId"));
            useradd.addAll(mySet);
            follows.setFollower(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
            follower.add((int)payload.get("userId"));
            follows = new Follows(id, follower ,following);
            followsRepository.save(follows);
        }

        return follows;

    }

    @PostMapping("/{id}/following")
    public Follows createFollowing   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(id);
        try {
            following = follows.getFollowing();
            Set<Integer> mySet = new HashSet<Integer>(following);
            mySet.add((int)payload.get("userId"));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
            following.add((int)payload.get("userId"));
            follows = new Follows(id, follower ,following);
            followsRepository.save(follows);
        }

        return follows;

    }
//
//    @PostMapping("/{id}/follows")
//    public Following createFollows   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
//        ArrayList<String> users = new ArrayList<>();
//        ArrayList<String> useradd = new ArrayList<>();
//        Following following;
//        following = followingRepository.findByUserId((int)payload.get("userId"));
//        try {
//            users = following.getUsers();
//            Set<String> mySet = new HashSet<String>(users);
//            mySet.add(id);
//            useradd.addAll(mySet);
//            following.setUsers(useradd);
//            followingRepository.save(following);
//        }catch (Exception e){
//            users.add(id);
//            following = new Following((int)payload.get("userId") ,users);
//            followingRepository.save(following);
//        }
//
//        return following;
//
//    }
//

    @DeleteMapping("/{id}/follows")
    public Follows deleteFollower   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(id);
        try {
            follower = follows.getFollower();
            Set<Integer> mySet = new HashSet<Integer>(follower);
            mySet.remove((int)payload.get("userId"));
            useradd.addAll(mySet);
            follows.setFollower(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
        }

        return follows;

    }

    @DeleteMapping("/{id}/following")
    public Follows deleteFollowing   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(id);
        try {
            following = follows.getFollowing();
            Set<Integer> mySet = new HashSet<Integer>(following);
            mySet.remove((int)payload.get("userId"));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
        }

        return follows;


    }
}

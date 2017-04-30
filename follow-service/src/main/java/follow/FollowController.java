package follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class FollowController {

    // For Annotation
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

    private static final String USERID = "userId";

    @Autowired
    private FollowsRepository followsRepository;
    
    @GetMapping("/{id}/follows")
    public Follows getFollows(@PathVariable("id") String id) {

        return followsRepository.findById(id);

    }


    @PostMapping("/{id}/follows")
    public Follows createFollowing   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        List<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(Integer.toString((Integer)payload.get(USERID)));
        try {
            follower = follows.getFollowing();
            Set<Integer> mySet = new HashSet<>(following);
            mySet.add(Integer.parseInt(id));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            following.add(Integer.parseInt(id));
            follows = new Follows(Integer.toString((Integer) payload.get(USERID)), follower ,following);
            followsRepository.save(follows);

        }

        Follows follows2;
        follows2 = followsRepository.findById(id);
        follower = new ArrayList<>();
        following = new ArrayList<>();
        useradd = new ArrayList<>();
        try {
            follower = follows2.getFollower();
            Set<Integer> mySet = new HashSet<>(follower);
            mySet.add((int)payload.get(USERID));
            useradd.addAll(mySet);
            follows2.setFollower(useradd);
            followsRepository.save(follows2);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            follower.add((int)payload.get(USERID));
            follows2 = new Follows(id, follower ,following);
            followsRepository.save(follows2);

        }

        return follows;

    }



    @DeleteMapping("/{id}/follows")
    public Follows deleteFollows   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        List<Integer> follower;
        List<Integer> following;

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(Integer.toString((Integer)payload.get(USERID)));
        try {
            following = follows.getFollowing();
            Set<Integer> mySet = new HashSet<>(following);
            mySet.remove(Integer.parseInt(id));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }

        useradd = new ArrayList<>();
        Follows follows2;
        follows2 = followsRepository.findById(id);
        try {
            follower = follows2.getFollower();
            Set<Integer> mySet = new HashSet<>(follower);
            mySet.remove(payload.get(USERID));
            useradd.addAll(mySet);
            follows2.setFollower(useradd);
            followsRepository.save(follows2);

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }

        return follows;

    }

}

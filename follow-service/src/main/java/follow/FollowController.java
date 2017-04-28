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

    @Autowired
    private FollowsRepository followsRepository;
    
    @GetMapping("/{id}/follows")
    public Follows getFollows(@PathVariable("id") String id) {

        Follows follows = followsRepository.findById(id);
        return follows;

    }


    @PostMapping("/{id}/follows")
    public Follows createFollowing   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower = new ArrayList<>();
        ArrayList<Integer> following = new ArrayList<>();

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(Integer.toString((Integer)payload.get("userId")));
        try {
            follower = follows.getFollowing();
            Set<Integer> mySet = new HashSet<>(following);
            mySet.add(Integer.parseInt(id));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
            following.add(Integer.parseInt(id));
            follows = new Follows(Integer.toString((Integer) payload.get("userId")), follower ,following);
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
            mySet.add((int)payload.get("userId"));
            useradd.addAll(mySet);
            follows2.setFollower(useradd);
            followsRepository.save(follows2);
        }catch (Exception e){
            follower.add((int)payload.get("userId"));
            follows2 = new Follows(id, follower ,following);
            followsRepository.save(follows2);
        }

        return follows;

    }



    @DeleteMapping("/{id}/follows")
    public Follows deleteFollows   (@PathVariable("id") String id, @RequestBody Map<String, Object> payload) {
        ArrayList<Integer> follower;
        ArrayList<Integer> following;

        ArrayList<Integer> useradd = new ArrayList<>();

        Follows follows;
        follows = followsRepository.findById(Integer.toString((Integer)payload.get("userId")));
        try {
            following = follows.getFollowing();
            Set<Integer> mySet = new HashSet<>(following);
            mySet.remove(Integer.parseInt(id));
            useradd.addAll(mySet);
            follows.setFollowing(useradd);
            followsRepository.save(follows);
        }catch (Exception e){
        }

        useradd = new ArrayList<>();
        Follows follows2;
        follows2 = followsRepository.findById(id);
        try {
            follower = follows2.getFollower();
            Set<Integer> mySet = new HashSet<>(follower);
            mySet.remove(payload.get("userId"));
            useradd.addAll(mySet);
            follows2.setFollower(useradd);
            followsRepository.save(follows2);

        }catch (Exception e){
        }

        return follows;

    }

}

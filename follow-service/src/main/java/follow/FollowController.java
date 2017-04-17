package follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(path = "/dummydata", method = RequestMethod.GET)
    public void postFollow() {
        List<User> followby = new ArrayList<User>();
        followby.add(new User(4,"Im Profile Picture", "I'm Name"));
        followby.add(new User(2,"Im Profile Picture", "I'm Name"));
        followby.add(new User(3,"Im Profile Picture", "I'm Name"));

        List<User> following = new ArrayList<User>();
        following.add(new User(4,"Im Profile following", "I'm following"));
        following.add(new User(2,"Im Profile following", "I'm following"));
        following.add(new User(3,"Im Profile following", "I'm following"));
//        mongoOperation.save(new FollowBy(1, follows));
        followByRepository.save(new FollowBy(1, followby));
        followingRepository.save(new Following(1,following));

    }

    @RequestMapping(path = "/{id}/follows", method = RequestMethod.GET)
    public Following getFollows(@PathVariable("id") Long id) {

//        Query query = new Query();
//        query.addCriteria(Criteria.where("userId").lt(1));

//        FollowBy foloFollowBy = mongoOperation.findOne(query, FollowBy.class);
//        try {
//            FollowBy foloFollowby = repository.findByUserId(1);
//            foloFollowby.setUserId(9999999);
//            foloFollowby.setId("1");
//            repository.save(foloFollowBy);
//        }
//        catch (Exception e){
//        }
        Following foloFollowing = followingRepository.findByUserId(id);
        return foloFollowing;

    }




}

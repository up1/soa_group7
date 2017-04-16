package follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/follows")
public class FollowController {

    // For Annotation
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

    @Autowired
    private FollowRepository repository;

    @RequestMapping(path = "/follow", method = RequestMethod.GET)
    public void postFollow() {
        List<Follow> follows = new ArrayList<Follow>();
        follows.add(new Follow(4,"Im Profile Picture", "I'm Name"));
        follows.add(new Follow(2,"Im Profile Picture", "I'm Name"));
        follows.add(new Follow(3,"Im Profile Picture", "I'm Name"));

//        mongoOperation.save(new FollowBy(1, follows));
        repository.save(new FollowBy(1, follows));
    }

    @RequestMapping(path = "/follow2", method = RequestMethod.GET)
    public void getFoll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").lt(1));

//        FollowBy foloFollowBy = mongoOperation.findOne(query, FollowBy.class);
        try {
            FollowBy foloFollowBy = repository.findByUserId(1);
            foloFollowBy.setUserId(9999999);
            foloFollowBy.setId("1");
            repository.save(foloFollowBy);
        }
        catch (Exception e){
        }

    }




}

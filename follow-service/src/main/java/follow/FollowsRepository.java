package follow;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by nut on 24/4/2560.
 */
public interface FollowsRepository extends MongoRepository<Follows,  String> {

    public Follows findById(String id);

}


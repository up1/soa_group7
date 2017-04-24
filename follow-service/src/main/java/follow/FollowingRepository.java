package follow;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by nut on 16/4/2560.
 */

public interface FollowingRepository extends MongoRepository<Following,  String> {

    public Following findByUserId(long userId);
}

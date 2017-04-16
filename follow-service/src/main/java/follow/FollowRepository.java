package follow;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowRepository extends MongoRepository<FollowBy, String> {

    public FollowBy findByUserId(long userId);
}
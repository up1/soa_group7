package follow;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowByRepository extends MongoRepository<FollowBy,  String> {

    public FollowBy findByUserId(long userId);
}
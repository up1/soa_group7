package follow;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by nut on 24/4/2560.
 */
@Document(collection = "follows")
public class Follows {
    @Id
    String id;

    List<Integer> follower;
    List<Integer> following;

    public Follows(){

    }

    public Follows(String userId, List<Integer> follower, List<Integer> following) {
        this.id = userId;
        this.follower = follower;
        this.following = following;
    }


    public List<Integer> getFollower() {
        return follower;
    }

    public void setFollower(List<Integer> follower) {
        this.follower = follower;
    }

    public List<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Integer> following) {
        this.following = following;
    }

}

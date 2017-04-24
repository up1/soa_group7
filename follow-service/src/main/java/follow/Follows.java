package follow;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by nut on 24/4/2560.
 */
@Document(collection = "follows")
public class Follows {
    @Id
    String id;

//    long userId;
    ArrayList<Integer> follower;
    ArrayList<Integer> following;

    public Follows() {
    }

    public Follows(String userId, ArrayList<Integer> follower, ArrayList<Integer> following) {
        this.id = userId;
        this.follower = follower;
        this.following = following;
    }


    public ArrayList<Integer> getFollower() {
        return follower;
    }

    public void setFollower(ArrayList<Integer> follower) {
        this.follower = follower;
    }

    public ArrayList<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Integer> following) {
        this.following = following;
    }

//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
}

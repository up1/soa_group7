package follow;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by nut on 15/4/2560.
 */
@Document(collection = "followBy")
public class FollowBy {
    @Id
    String id;
    long userId;
    List<User> follows;

    public FollowBy() {
    }

    public FollowBy(long userId, List<User> follows) {
        this.userId = userId;
        this.follows = follows;
    }

    public FollowBy(List<User> follows) {
        this.follows = follows;
    }

    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

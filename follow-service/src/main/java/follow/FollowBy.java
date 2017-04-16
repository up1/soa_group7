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
    List<User> users;

    public FollowBy() {
    }

    public FollowBy(long userId, List<User> users) {
        this.userId = userId;
        this.users = users;

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

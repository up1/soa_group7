package follow;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by nut on 30/4/2560.
 */
public class Follow {
    @Id
    String id;
    long userId;
    List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

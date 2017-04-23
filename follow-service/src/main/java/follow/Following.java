package follow;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nut on 16/4/2560.
 */

@Document(collection = "following")
public class Following {
    @Id
    String id;

    long userId;
    ArrayList<String> users;

    public Following() {
    }

    public Following(long userId, ArrayList<String> users) {
        this.userId = userId;
        this.users = users;

    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}


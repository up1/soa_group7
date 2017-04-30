package follow;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by nut on 15/4/2560.
 */

@Document(collection = "followBy")
public class FollowBy extends Follow{

    public FollowBy(long userId, List<String> users) {
        this.userId = userId;
        this.users = users;
    }
}

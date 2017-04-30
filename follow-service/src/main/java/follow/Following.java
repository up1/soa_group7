package follow;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by nut on 16/4/2560.
 */

@Document(collection = "following")
public class Following extends Follow{

    public Following(long userId, List<String> users) {
        this.userId = userId;
        this.users = users;
    }
}

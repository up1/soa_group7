package com.shenzhentagram.model;

import java.util.List;

/**
 * Created by Meranote on 5/1/2017.
 */
public class Follower {

    private List<User> follower;

    public List<User> getFollower() {
        return follower;
    }

    public void setFollower(List<User> follower) {
        this.follower = follower;
    }
}

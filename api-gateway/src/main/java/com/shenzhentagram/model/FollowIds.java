package com.shenzhentagram.model;

import java.util.List;

/**
 * Created by Meranote on 5/1/2017.
 */
public class FollowIds {

    private List<Integer> follower;
    private List<Integer> following;

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

package com.shenzhentagram.posts;

/**
 * Created by phompang on 3/4/2017 AD.
 */
public class Post {
    private long id;
    private String type;
    private int comments;
    private String caption;
    private int reaction;
    private long created_at;
    private long updated_at;

    public Post(long id, String type, int comments, String caption, int reaction, long created_at, long updated_at) {
        this.id = id;
        this.type = type;
        this.comments = comments;
        this.caption = caption;
        this.reaction = reaction;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }
}

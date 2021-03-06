package com.shenzhentagram.models;

/**
 * Created by Jiravat on 3/13/2017.
 */
public class NotificationPost extends NotificationAbstract {

    long post_id;

    String comment_id;

    public NotificationPost(long id, long post_id, String comment_id) {
        this.id = id;
        this.post_id = post_id;
        this.comment_id = comment_id;
    }

    public NotificationPost() {
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }
}

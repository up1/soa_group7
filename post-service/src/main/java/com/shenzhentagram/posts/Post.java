package com.shenzhentagram.posts;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by phompang on 3/4/2017 AD.
 */
@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "INT DEFAULT '0'")
    private int comments;

    private String caption;

    @Column(nullable = false, columnDefinition = "INT DEFAULT '0'")
    private int reactions;

    @Column(nullable = false)
    private String media;

    @Column(nullable = false)
    private long user_id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date updated_at;

    public Post(String type, int comments, String caption, int reactions, String media, long user_id, Date created_at, Date updated_at) {
        this.setType(type);
        this.setComments(comments);
        this.setCaption(caption);
        this.setReactions(reactions);
        this.setMedia(media);
        this.setUser_id(user_id);
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
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

    public int getReactions() {
        return reactions;
    }

    public void setReactions(int reactions) {
        this.reactions = reactions;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

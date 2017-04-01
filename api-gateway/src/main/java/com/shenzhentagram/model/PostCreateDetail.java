package com.shenzhentagram.model;

/**
 * Created by Meranote on 4/1/2017.
 */
public class PostCreateDetail {

    private String caption;
    private String type;
    private String file;
    private long user_id;

    public PostCreateDetail() {
    }

    public PostCreateDetail(String caption, String type, String file, long user_id) {
        this.caption = caption;
        this.type = type;
        this.file = file;
        this.user_id = user_id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

}

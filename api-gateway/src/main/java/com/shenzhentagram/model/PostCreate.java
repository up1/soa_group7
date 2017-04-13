package com.shenzhentagram.model;

/**
 * Created by Meranote on 4/1/2017.
 */
public class PostCreate extends PostBase {

    private long user_id;
    private String file;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}

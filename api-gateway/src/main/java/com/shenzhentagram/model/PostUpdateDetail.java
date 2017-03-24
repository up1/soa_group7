package com.shenzhentagram.model;

/**
 * Created by Meranote on 3/20/2017.
 */
public class PostUpdateDetail {

    private int id;
    private String caption;

    public PostUpdateDetail() { }

    public PostUpdateDetail(int id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}

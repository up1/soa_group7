package com.shenzhentagram.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.mapping.RootClass;

/**
 * Created by Jiravat on 3/14/2017.
 */


abstract public class NotificationAbstract {
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

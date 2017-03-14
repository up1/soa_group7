package com.shenzhentagram.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Created by Jiravat on 3/14/2017.
 */
public class NotificationDeserializer extends JsonDeserializer<Notification> {

    @Override
    public Notification deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

            Notification notification = new Notification();

            notification.setId(node.get("id").longValue());
            notification.setNotificationId(node.get("notificationId").longValue());
            notification.setText(node.get("text").textValue());
            notification.setCheckStatus(node.get("checkStatus").intValue());
            notification.setThumbnail(node.get("thumbnail").textValue());
            notification.setType(node.get("type").textValue());
            JsonNode notificationNode = node.get("type");
            switch (notification.getType()) {
                case "followed_by":
                    notification.setNotification(new NotificationUser(notificationNode.get("id").longValue(), notificationNode.get("userId").longValue()));
                    break;
                case "comment":
                    notification.setNotification(new NotificationPost(notificationNode.get("id").longValue(), notificationNode.get("post_id").longValue(), notificationNode.get("comment_id").longValue()));
                    break;
                case "reaction":
                    notification.setNotification(new NotificationReaction(notificationNode.get("id").longValue(), notificationNode.get("reaction_id").longValue()));
                    break;
            }

            return notification;
        }
        catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }
}

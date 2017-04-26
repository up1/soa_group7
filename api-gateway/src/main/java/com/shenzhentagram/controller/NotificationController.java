package com.shenzhentagram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/notifications", produces = {MediaType.APPLICATION_JSON_VALUE})
public class NotificationController extends TemplateRestController {

    public NotificationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "notification");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNotification(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/notifications/{id}", Object.class, id);
    }

    @GetMapping()
    public ResponseEntity<ArrayList> getSelfNotifications(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) throws Exception {
        String uri = String.format("/notifications?limit=%d&page=%d&userId=%d",
                limit,
                page,
                getAuthenticatedUser().getId()
        );

        return request(HttpMethod.GET, uri, ArrayList.class);
    }

    @PatchMapping("/status/checked")
    public ResponseEntity<Void> checkedNotification(
            @RequestBody Map<String, Object> notificationIds
    ) {
        return request(HttpMethod.PATCH, "/notifications/status/checked", notificationIds, Void.class);
    }

    @PatchMapping("/status/unchecked")
    public ResponseEntity<Void> uncheckedNotification(
            @RequestBody Map<String, Object> notificationIds
    ) {
        return request(HttpMethod.PATCH, "/notifications/status/unchecked", notificationIds, Void.class);
    }

    /**
     * [Internal only] create new follower notification
     */
    public ResponseEntity<Void> createFollowerNotification(
            int targetUserId, int targetFollowerId
    ) {
        // Create empty base notification
        Notification notification = new Notification();
        notification.setUserId(targetUserId);
        notification.setType("followed_by");
        notification.setText("New follower: %follower.display_name%");
        notification.setThumbnail("%follower.profile_picture%");

        // NOTE: %follower.property% will map to follower's property
        // Ex. %follower.display_name% will map to follower's display_name property
        // (Hardcoded Templating)

        // Create empty sub-type notification
        NotificationUser subNotification = new NotificationUser();
        subNotification.setUserId(targetFollowerId);

        // Set sub-type to base
        notification.setFrom(subNotification);

        // AND WHY THE FUCK I NEED TO SENT IT AS ARRAY JUST TO CREATE ONLY ONE NOTIFICATION -_-
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        // Send to notification-service
        return request(HttpMethod.POST, "/notifications/follow", notifications, Void.class);
    }

    /**
     * [Internal only] create new comment notification
     */
    public ResponseEntity<Void> createCommentNotification(
            int targetUserId, int targetPostId, String targetCommentId
    ) {
        // Create empty base notification
        Notification notification = new Notification();
        notification.setUserId(targetUserId);
        notification.setType("comment");
        notification.setText("New comment on your post");
        notification.setThumbnail("%post.media%");

        // NOTE: %post.property% will map to post's property
        // Ex. %post.media% will map to post's media property
        // (Hardcoded Templating)

        // Create empty sub-type notification
        NotificationPost subNotification = new NotificationPost();
        subNotification.setPost_id(targetPostId);
        subNotification.setComment_id(targetCommentId);

        // Set sub-type to base
        notification.setFrom(subNotification);

        // AND WHY THE FUCK I NEED TO SENT IT AS ARRAY JUST TO CREATE ONLY ONE NOTIFICATION -_-
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        try {
            log.info(new ObjectMapper().writeValueAsString(notifications));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Send to notification-service
        return request(HttpMethod.POST, "/notifications/comment", notifications, Void.class);
    }

    /**
     * [Internal only] create new reaction notification
     */
    public ResponseEntity<Void> createReactionNotification(
            int targetUserId, int targetPostId, int targetReactionId
    ) {
        // Create empty base notification
        Notification notification = new Notification();
        notification.setUserId(targetUserId);
        notification.setType("reaction");
        notification.setText("New reaction on your post");
        notification.setThumbnail("%post.media%");

        // NOTE: %post.property% will map to post's property
        // Ex. %post.media% will map to post's media property
        // (Hardcoded Templating)

        // Create empty sub-type notification
        NotificationReaction subNotification = new NotificationReaction();
        subNotification.setPost_id(targetPostId);
        subNotification.setReaction_id(targetReactionId);

        // Set sub-type to base
        notification.setFrom(subNotification);

        // AND WHY THE FUCK I NEED TO SENT IT AS ARRAY JUST TO CREATE ONLY ONE NOTIFICATION -_-
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        // Send to notification-service
        return request(HttpMethod.POST, "/notifications/reaction", notifications, Void.class);
    }

}

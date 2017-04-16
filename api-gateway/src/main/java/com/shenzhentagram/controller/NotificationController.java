package com.shenzhentagram.controller;

import com.shenzhentagram.services.notification.model.Notification;
import com.shenzhentagram.services.notification.model.NotificationList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/notifications", produces = {MediaType.APPLICATION_JSON_VALUE})
public class NotificationController extends TemplateRestController {

    public NotificationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "notification");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/notifications/{id}", Notification.class, id);
    }

    @GetMapping()
    public ResponseEntity<NotificationList> getSelfNotifications(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) throws Exception {
        String uri = String.format("/notifications?limit=%d&page=%d&userId=%d",
                limit,
                page,
                getAuthenticatedUser().getId()
        );

        return request(HttpMethod.GET, uri, NotificationList.class);
    }

    @PatchMapping("/status/checked")
    public ResponseEntity<Void> checkedNotification(
            @RequestBody List<Integer> notificationIds
    ) {
        return request(HttpMethod.PATCH, "/notification/status/checked", notificationIds, Void.class);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateNotification(
            @RequestBody List<Notification> notifications
    ) {
        return request(HttpMethod.POST, "/notification/update", notifications, Void.class);
    }

    /**
     * [Internal only] create new notification
     */
    public ResponseEntity<Void> createNotifications(
            List<Notification> notifications
    ) {
        return request(HttpMethod.POST, "/notification/create", notifications, Void.class);
    }

}

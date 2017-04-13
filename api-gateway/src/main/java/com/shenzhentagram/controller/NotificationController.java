package com.shenzhentagram.controller;

import com.shenzhentagram.model.Notification;
import com.shenzhentagram.model.NotificationList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<NotificationList> getNotificationsByUser(
            @RequestParam("limit") int limit,
            @RequestParam("page") int page
    ) {
        return request(HttpMethod.GET, "/notifications?limit=" + limit + "&page=" + page, NotificationList.class);
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

package com.shenzhentagram.controller;

import com.shenzhentagram.model.Notification;
import com.shenzhentagram.model.NotificationList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Meranote on 4/2/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/notifications")
public class NotificationController extends TemplateRestController {

    public NotificationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "notification");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(
            @PathVariable("id") long id
    ) throws IOException {
        return request(HttpMethod.GET, "/notifications/{id}", Notification.class, id);
    }

    @GetMapping()
    public ResponseEntity<NotificationList> getNotificationsByUser(
            @RequestParam("limit") int limit,
            @RequestParam("page") int page
    ) {
        return requestWithAuth(HttpMethod.GET, "/notifications?limit=" + limit + "&page=" + page, NotificationList.class);
    }

    /**
     * [Internal only] create new notification
     */
    public ResponseEntity<Void> createNotifications(
            List<Notification> notifications
    ) {
        return requestWithAuth(HttpMethod.POST, "/notification/create", notifications, Void.class);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateNotification(
            @RequestBody List<Notification> notifications
    ) {
        return requestWithAuth(HttpMethod.POST, "/notification/update", notifications, Void.class);
    }

}

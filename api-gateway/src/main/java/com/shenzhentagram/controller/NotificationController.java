package com.shenzhentagram.controller;

import com.shenzhentagram.model.Notification;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Meranote on 4/2/2017.
 */
@RestController
public class NotificationController extends TemplateRestController {

    // FIXME Change return/response interface to class (implement the class)

    public NotificationController(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        super(environment, restTemplateBuilder, "notification");
    }

    @GetMapping("/{id}")
    public Notification getNotification(
            @PathVariable("id") long id
    ) {
        return request(HttpMethod.GET, "/notifications/{id}", Notification.class, id);
    }

    @GetMapping("/notifications")
    public List<Notification> getNotificationsByUser(
            @RequestParam("limit") int limit,
            @RequestParam("page") int page
    ) {
        return requestWithAuth(HttpMethod.GET, "/notifications?limit=" + limit + "&page=" + page, List.class);
    }

    /**
     * [Internal only] create new notification
     */
    public void createNotifications(
            List<Notification> notifications
    ) {
        // TODO Implement to any resource that cause the new notification
        requestWithAuth(HttpMethod.POST, "/notification/create", notifications, Void.class);
    }

    @PutMapping("/notifications/update")
    public void updateNotification(
            @RequestBody List<Notification> notifications
    ) {
        requestWithAuth(HttpMethod.POST, "/notification/create", notifications, Void.class);
    }

}

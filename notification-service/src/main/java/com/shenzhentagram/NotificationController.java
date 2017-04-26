package com.shenzhentagram;

import com.shenzhentagram.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/{notification_id}")
    public Notification getNotification(
            @PathVariable("notification_id") long id
    ) {
        return this.notificationRepository.findById(id);
    }

    @PutMapping("/{notification_id}")
    public void updateNotification(
            @PathVariable("notification_id") long id,
            @RequestBody Notification notification,
            HttpServletResponse response
    ) {
        int SC = this.notificationRepository.updateNotification(notification, id);
        response.setStatus(SC);
    }

    @PatchMapping(path = "/status/checked")
    public ResponseEntity<Void> checkedNotifications(
            @RequestBody Map<String, Object> body
    ) {
        return new ResponseEntity<>(HttpStatus.valueOf(callUpdateStatus((List<Object>) body.get("id"), 1)));
    }

    @PatchMapping(path = "/status/unchecked")
    public ResponseEntity<Void> uncheckedNotifications(
            @RequestBody Map<String, Object> body
    ) {
        return new ResponseEntity<>(HttpStatus.valueOf(callUpdateStatus((List<Object>) body.get("id"), 0)));
    }

    private int callUpdateStatus(List<Object> idObjectList, int status) {
        List<Integer> ids = new ArrayList<>();
        idObjectList.forEach((id) -> ids.add((Integer) id));

        return this.notificationRepository.updateNotificationsStatus(ids, status);
    }

    @GetMapping()
    public List<Notification> getNotificationsByUser(
            @RequestParam("limit") int limit,
            @RequestParam("page") int page,
            @RequestParam("userId") long id
    ) {
        return this.notificationRepository.findByUserId(id, limit, page);
    }

    @PutMapping("/notifications")
    public void updateNotifications(
            @RequestBody List<Notification> notifications,
            HttpServletResponse response
    ) {
        int SC = this.notificationRepository.updateNotifications(notifications);
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/comment")
    public void createNotificationPost(
            @RequestBody List<Notification> notifications,
            HttpServletResponse response
    ) {
        int SC = this.notificationRepository.createNotifications(notifications, "comment");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/reaction")
    public void createNotificationReaction(
            @RequestBody List<Notification> notifications,
            HttpServletResponse response
    ) {
        int SC = this.notificationRepository.createNotifications(notifications, "reaction");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/follow")
    public void createNotificationUser(
            @RequestBody List<Notification> notifications,
            HttpServletResponse response
    ) {
        int SC = this.notificationRepository.createNotifications(notifications, "followed_by");
        response.setStatus(SC);
    }

}

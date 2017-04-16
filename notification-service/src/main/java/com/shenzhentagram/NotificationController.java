package com.shenzhentagram;

import com.shenzhentagram.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @RequestMapping(method = RequestMethod.GET , path = "/notifications/{notification_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Notification getNotification(
            @PathVariable("notification_id") long id
    ) {
        return this.notificationRepository.findById(id);
    }


    @RequestMapping(method = RequestMethod.PUT , path = "/notifications/{notification_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void updateNotification(@PathVariable("notification_id") long id, @RequestBody Notification notification, HttpServletResponse response) {
        int SC = this.notificationRepository.updateNotification(notification, id);
        response.setStatus(SC);
    }

    @PatchMapping(path = "/notifications/status/checked")
    public ResponseEntity<Void> checkedNotifications(
            @RequestBody List<Integer> notificationIds
    ) {
        int SC = this.notificationRepository.updateNotificationsStatus(notificationIds, 1);
        return new ResponseEntity<>(HttpStatus.valueOf(SC));
    }

    @PatchMapping(path = "/notifications/status/unchecked")
    public ResponseEntity<Void> uncheckedNotifications(
            @RequestBody List<Integer> notificationIds
    ) {
        int SC = this.notificationRepository.updateNotificationsStatus(notificationIds, 0);
        return new ResponseEntity<>(HttpStatus.valueOf(SC));
    }

    @RequestMapping(method = RequestMethod.GET , path = "/notifications", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Notification> getNotificationsByUser(@RequestParam("limit") int limit,@RequestParam("page") int page,@RequestParam("userId") long id) {

        return this.notificationRepository.findByUserId(id, limit, page);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications")
    public void createNotifications(@RequestBody List<Notification> notifications, HttpServletResponse response) {
        int SC = this.notificationRepository.createNotifications(notifications);
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.PUT , path = "/notifications", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void updateNotifications(@RequestBody List<Notification> notifications, HttpServletResponse response) {
        int SC = this.notificationRepository.updateNotifications(notifications);
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/comment")
    public void createNotificationPost(@RequestBody List<Notification> notifications, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotifications(notifications, "comment");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/reaction")
    public void createNotificationReaction(@RequestBody List<Notification> notifications, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotifications(notifications, "reaction");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/follow")
    public void createNotificationUser(@RequestBody List<Notification> notifications, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotifications(notifications, "followed_by");
        response.setStatus(SC);
    }

}

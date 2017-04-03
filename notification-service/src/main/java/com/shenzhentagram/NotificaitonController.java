package com.shenzhentagram;

import com.shenzhentagram.authentication.JWTAuthenticationService;
import com.shenzhentagram.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class NotificaitonController {

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

    @RequestMapping(method = RequestMethod.GET , path = "/notifications", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Notification> getNotificationsByUser(@RequestParam("limit") int limit,@RequestParam("page") int page,HttpServletRequest request) {
        JWTAuthenticationService authen = new JWTAuthenticationService();
        authen.parseToken(request);
        long id = authen.getUserId();
        return this.notificationRepository.findByUserId(id, limit, page);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/create")
    public void createNotifications(@RequestBody List<Notification> notifications, HttpServletResponse response) {
        int SC = this.notificationRepository.createNotifications(notifications);
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.PUT , path = "/notifications/update", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void updateNotifications(@RequestBody List<Notification> notifications, HttpServletResponse response) {
        int SC = this.notificationRepository.updateNotifications(notifications);
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/comment")
    public void createNotificationPost(@RequestBody Notification notification, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotification(notification, "comment");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/reaction")
    public void createNotificationReaction(@RequestBody Notification notification, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotification(notification, "reaction");
        response.setStatus(SC);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/notifications/follow")
    public void createNotificationUser(@RequestBody Notification notification, HttpServletResponse response) {

        int SC = this.notificationRepository.createNotification(notification, "followed_by");
        response.setStatus(SC);
    }

}

package com.shenzhentagram;

import com.shenzhentagram.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class NotificaitonController {

    @Autowired
    private NotificationRepository notificaitonRepository;

    @RequestMapping(method = RequestMethod.GET , path = "/notifications/{notification_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Notification getUser(
            @PathVariable("notification_id") long id
    ) {
        return this.notificaitonRepository.findById(id);
    }

}

package com.shenzhentagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

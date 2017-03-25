package com.shenzhentagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jiravat on 3/9/2017.
 */
@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class NotificationServiceApplication {

    public static void main(String ... args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

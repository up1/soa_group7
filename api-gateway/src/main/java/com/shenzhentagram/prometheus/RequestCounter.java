package com.shenzhentagram.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;

/**
 * Created by Jiravat on 4/27/2017.
 */
public class RequestCounter {
    //Request
    public static final Summary requestLatency = Summary.build()
            .name("API_Gateway_Requests_Latency_Seconds")
            .help("Request latency in seconds. /*").register();
    public static final Counter requestSuccessCounter = Counter.build()
            .name("API_Gateway_Request_Success")
            .help("Request All Success Counter. /*").register();
    public static final Counter requestFailCounter = Counter.build()
            .name("API_Gateway_Request_Failed")
            .help("Request All Failed Counter. /*").register();
    //Authentication
    public static final Counter authenSuccessCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_Success")
            .help("Request Authentication Success Counter /auth.").register();
    public static final Counter authenFailedCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_Failed")
            .help("Request Authentication Failed Counter /auth.").register();
    //User
    public static final Counter userSuccessCounter = Counter.build()
            .name("API_Gateway_Request_User_Success")
            .help("Request User Success Counter /users.").register();
    public static final Counter userFailedCounter = Counter.build()
            .name("API_Gateway_Request_User_Failed")
            .help("Request User Failed Counter /users.").register();
    //Comment
    public static final Counter commentSuccessCounter = Counter.build()
            .name("API_Gateway_Request_Comment_Success")
            .help("Request Comment Success Counter /comments.").register();
    public static final Counter commentFailedCounter = Counter.build()
            .name("API_Gateway_Request_Comment_Failed")
            .help("Request Comment Failed Counter /comments.").register();
    //Post
    public static final Counter postSuccessCounter = Counter.build()
            .name("API_Gateway_Request_Post_Success")
            .help("Request Post Success Counter /posts.").register();
    //Notification
    public static final Counter notificationSuccessCounter = Counter.build()
            .name("API_Gateway_Request_Notification_Success")
            .help("Request Notification Success Counter /notifications.").register();
    //Follow
    public static final Counter postFollowCounter = Counter.build()
            .name("API_Gateway_Request_Follow_Success")
            .help("Request Follow Success Counter /posts.").register();
}

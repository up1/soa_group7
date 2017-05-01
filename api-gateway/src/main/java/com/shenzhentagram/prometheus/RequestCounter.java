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
    //Prometheus Count Request
    public static void countRequestRequest(int statusCode){
        switch (statusCode){
            case 200:
                RequestOKCounter.inc();
                break;
            case 201:
                RequestCreatedCounter.inc();
                break;
            case 204:
                RequestNoContentCounter.inc();
                break;
            case 400:
                RequestBasRequestCounter.inc();
                break;
            case 401:
                RequestUnauthorizedCounter.inc();
                break;
            case 403:
                RequestForbiddenCounter.inc();
                break;
            case 404:
                RequestNotFoundCounter.inc();
                break;
            case 500:
                RequestInternalServerErrorCounter.inc();
                break;
            case 503:
                RequestServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter RequestOKCounter = Counter.build()
            .name("API_Gateway_Request_Request_OK_200")
            .help("Request Request OK 200 Counter").register();
    private static final Counter RequestCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Request_Created_201")
            .help("Request Request Created 201 Counter").register();
    private static final Counter RequestNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Request_NoContent_204")
            .help("Request Request NoContent 204 Counter").register();
    private static final Counter RequestBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Request_BasRequest_400")
            .help("Request Request BasRequest 400 Counter").register();
    private static final Counter RequestUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Request_Unauthorized_401")
            .help("Request Request Unauthorized 401 Counter").register();
    private static final Counter RequestForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Request_Forbidden_403")
            .help("Request Request Forbidden 403 Counter").register();
    private static final Counter RequestNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Request_NotFound_404")
            .help("Request Request NotFound 404 Counter").register();
    private static final Counter RequestInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Request_InternalServerError_500")
            .help("Request Request InternalServerError 500 Counter").register();
    private static final Counter RequestServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Request_ServiceUnavailable_503")
            .help("Request Request ServiceUnavailable 503 Counter").register();

    //Prometheus Count Authentication
    public static void countAuthenticationRequest(int statusCode){
        switch (statusCode){
            case 200:
                AuthenticationOKCounter.inc();
                break;
            case 201:
                AuthenticationCreatedCounter.inc();
                break;
            case 204:
                AuthenticationNoContentCounter.inc();
                break;
            case 400:
                AuthenticationBasRequestCounter.inc();
                break;
            case 401:
                AuthenticationUnauthorizedCounter.inc();
                break;
            case 403:
                AuthenticationForbiddenCounter.inc();
                break;
            case 404:
                AuthenticationNotFoundCounter.inc();
                break;
            case 500:
                AuthenticationInternalServerErrorCounter.inc();
                break;
            case 503:
                AuthenticationServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter AuthenticationOKCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_OK_200")
            .help("Request Authentication OK 200 Counter").register();
    private static final Counter AuthenticationCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_Created_201")
            .help("Request Authentication Created 201 Counter").register();
    private static final Counter AuthenticationNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_NoContent_204")
            .help("Request Authentication NoContent 204 Counter").register();
    private static final Counter AuthenticationBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_BasRequest_400")
            .help("Request Authentication BasRequest 400 Counter").register();
    private static final Counter AuthenticationUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_Unauthorized_401")
            .help("Request Authentication Unauthorized 401 Counter").register();
    private static final Counter AuthenticationForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_Forbidden_403")
            .help("Request Authentication Forbidden 403 Counter").register();
    private static final Counter AuthenticationNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_NotFound_404")
            .help("Request Authentication NotFound 404 Counter").register();
    private static final Counter AuthenticationInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_InternalServerError_500")
            .help("Request Authentication InternalServerError 500 Counter").register();
    private static final Counter AuthenticationServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Authentication_ServiceUnavailable_503")
            .help("Request Authentication ServiceUnavailable 503 Counter").register();

    //Prometheus Count Comment
    public static void countCommentRequest(int statusCode){
        switch (statusCode){
            case 200:
                CommentOKCounter.inc();
                break;
            case 201:
                CommentCreatedCounter.inc();
                break;
            case 204:
                CommentNoContentCounter.inc();
                break;
            case 400:
                CommentBasRequestCounter.inc();
                break;
            case 401:
                CommentUnauthorizedCounter.inc();
                break;
            case 403:
                CommentForbiddenCounter.inc();
                break;
            case 404:
                CommentNotFoundCounter.inc();
                break;
            case 500:
                CommentInternalServerErrorCounter.inc();
                break;
            case 503:
                CommentServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter CommentOKCounter = Counter.build()
            .name("API_Gateway_Request_Comment_OK_200")
            .help("Request Comment OK 200 Counter").register();
    private static final Counter CommentCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Comment_Created_201")
            .help("Request Comment Created 201 Counter").register();
    private static final Counter CommentNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Comment_NoContent_204")
            .help("Request Comment NoContent 204 Counter").register();
    private static final Counter CommentBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Comment_BasRequest_400")
            .help("Request Comment BasRequest 400 Counter").register();
    private static final Counter CommentUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Comment_Unauthorized_401")
            .help("Request Comment Unauthorized 401 Counter").register();
    private static final Counter CommentForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Comment_Forbidden_403")
            .help("Request Comment Forbidden 403 Counter").register();
    private static final Counter CommentNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Comment_NotFound_404")
            .help("Request Comment NotFound 404 Counter").register();
    private static final Counter CommentInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Comment_InternalServerError_500")
            .help("Request Comment InternalServerError 500 Counter").register();
    private static final Counter CommentServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Comment_ServiceUnavailable_503")
            .help("Request Comment ServiceUnavailable 503 Counter").register();

    //Prometheus Count Follow
    public static void countFollowRequest(int statusCode){
        switch (statusCode){
            case 200:
                FollowOKCounter.inc();
                break;
            case 201:
                FollowCreatedCounter.inc();
                break;
            case 204:
                FollowNoContentCounter.inc();
                break;
            case 400:
                FollowBasRequestCounter.inc();
                break;
            case 401:
                FollowUnauthorizedCounter.inc();
                break;
            case 403:
                FollowForbiddenCounter.inc();
                break;
            case 404:
                FollowNotFoundCounter.inc();
                break;
            case 500:
                FollowInternalServerErrorCounter.inc();
                break;
            case 503:
                FollowServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter FollowOKCounter = Counter.build()
            .name("API_Gateway_Request_Follow_OK_200")
            .help("Request Follow OK 200 Counter").register();
    private static final Counter FollowCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Follow_Created_201")
            .help("Request Follow Created 201 Counter").register();
    private static final Counter FollowNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Follow_NoContent_204")
            .help("Request Follow NoContent 204 Counter").register();
    private static final Counter FollowBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Follow_BasRequest_400")
            .help("Request Follow BasRequest 400 Counter").register();
    private static final Counter FollowUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Follow_Unauthorized_401")
            .help("Request Follow Unauthorized 401 Counter").register();
    private static final Counter FollowForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Follow_Forbidden_403")
            .help("Request Follow Forbidden 403 Counter").register();
    private static final Counter FollowNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Follow_NotFound_404")
            .help("Request Follow NotFound 404 Counter").register();
    private static final Counter FollowInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Follow_InternalServerError_500")
            .help("Request Follow InternalServerError 500 Counter").register();
    private static final Counter FollowServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Follow_ServiceUnavailable_503")
            .help("Request Follow ServiceUnavailable 503 Counter").register();

    //Prometheus Count Notification
    public static void countNotificationRequest(int statusCode){
        switch (statusCode){
            case 200:
                NotificationOKCounter.inc();
                break;
            case 201:
                NotificationCreatedCounter.inc();
                break;
            case 204:
                NotificationNoContentCounter.inc();
                break;
            case 400:
                NotificationBasRequestCounter.inc();
                break;
            case 401:
                NotificationUnauthorizedCounter.inc();
                break;
            case 403:
                NotificationForbiddenCounter.inc();
                break;
            case 404:
                NotificationNotFoundCounter.inc();
                break;
            case 500:
                NotificationInternalServerErrorCounter.inc();
                break;
            case 503:
                NotificationServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter NotificationOKCounter = Counter.build()
            .name("API_Gateway_Request_Notification_OK_200")
            .help("Request Notification OK 200 Counter").register();
    private static final Counter NotificationCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Notification_Created_201")
            .help("Request Notification Created 201 Counter").register();
    private static final Counter NotificationNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Notification_NoContent_204")
            .help("Request Notification NoContent 204 Counter").register();
    private static final Counter NotificationBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Notification_BasRequest_400")
            .help("Request Notification BasRequest 400 Counter").register();
    private static final Counter NotificationUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Notification_Unauthorized_401")
            .help("Request Notification Unauthorized 401 Counter").register();
    private static final Counter NotificationForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Notification_Forbidden_403")
            .help("Request Notification Forbidden 403 Counter").register();
    private static final Counter NotificationNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Notification_NotFound_404")
            .help("Request Notification NotFound 404 Counter").register();
    private static final Counter NotificationInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Notification_InternalServerError_500")
            .help("Request Notification InternalServerError 500 Counter").register();
    private static final Counter NotificationServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Notification_ServiceUnavailable_503")
            .help("Request Notification ServiceUnavailable 503 Counter").register();

    //Prometheus Count Post
    public static void countPostRequest(int statusCode){
        switch (statusCode){
            case 200:
                PostOKCounter.inc();
                break;
            case 201:
                PostCreatedCounter.inc();
                break;
            case 204:
                PostNoContentCounter.inc();
                break;
            case 400:
                PostBasRequestCounter.inc();
                break;
            case 401:
                PostUnauthorizedCounter.inc();
                break;
            case 403:
                PostForbiddenCounter.inc();
                break;
            case 404:
                PostNotFoundCounter.inc();
                break;
            case 500:
                PostInternalServerErrorCounter.inc();
                break;
            case 503:
                PostServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter PostOKCounter = Counter.build()
            .name("API_Gateway_Request_Post_OK_200")
            .help("Request Post OK 200 Counter").register();
    private static final Counter PostCreatedCounter = Counter.build()
            .name("API_Gateway_Request_Post_Created_201")
            .help("Request Post Created 201 Counter").register();
    private static final Counter PostNoContentCounter = Counter.build()
            .name("API_Gateway_Request_Post_NoContent_204")
            .help("Request Post NoContent 204 Counter").register();
    private static final Counter PostBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_Post_BasRequest_400")
            .help("Request Post BasRequest 400 Counter").register();
    private static final Counter PostUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_Post_Unauthorized_401")
            .help("Request Post Unauthorized 401 Counter").register();
    private static final Counter PostForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_Post_Forbidden_403")
            .help("Request Post Forbidden 403 Counter").register();
    private static final Counter PostNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_Post_NotFound_404")
            .help("Request Post NotFound 404 Counter").register();
    private static final Counter PostInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_Post_InternalServerError_500")
            .help("Request Post InternalServerError 500 Counter").register();
    private static final Counter PostServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_Post_ServiceUnavailable_503")
            .help("Request Post ServiceUnavailable 503 Counter").register();

    //Prometheus Count User
    public static void countUserRequest(int statusCode){
        switch (statusCode){
            case 200:
                UserOKCounter.inc();
                break;
            case 201:
                UserCreatedCounter.inc();
                break;
            case 204:
                UserNoContentCounter.inc();
                break;
            case 400:
                UserBasRequestCounter.inc();
                break;
            case 401:
                UserUnauthorizedCounter.inc();
                break;
            case 403:
                UserForbiddenCounter.inc();
                break;
            case 404:
                UserNotFoundCounter.inc();
                break;
            case 500:
                UserInternalServerErrorCounter.inc();
                break;
            case 503:
                UserServiceUnavailableCounter.inc();
                break;
        }
    }
    private static final Counter UserOKCounter = Counter.build()
            .name("API_Gateway_Request_User_OK_200")
            .help("Request User OK 200 Counter").register();
    private static final Counter UserCreatedCounter = Counter.build()
            .name("API_Gateway_Request_User_Created_201")
            .help("Request User Created 201 Counter").register();
    private static final Counter UserNoContentCounter = Counter.build()
            .name("API_Gateway_Request_User_NoContent_204")
            .help("Request User NoContent 204 Counter").register();
    private static final Counter UserBasRequestCounter = Counter.build()
            .name("API_Gateway_Request_User_BasRequest_400")
            .help("Request User BasRequest 400 Counter").register();
    private static final Counter UserUnauthorizedCounter = Counter.build()
            .name("API_Gateway_Request_User_Unauthorized_401")
            .help("Request User Unauthorized 401 Counter").register();
    private static final Counter UserForbiddenCounter = Counter.build()
            .name("API_Gateway_Request_User_Forbidden_403")
            .help("Request User Forbidden 403 Counter").register();
    private static final Counter UserNotFoundCounter = Counter.build()
            .name("API_Gateway_Request_User_NotFound_404")
            .help("Request User NotFound 404 Counter").register();
    private static final Counter UserInternalServerErrorCounter = Counter.build()
            .name("API_Gateway_Request_User_InternalServerError_500")
            .help("Request User InternalServerError 500 Counter").register();
    private static final Counter UserServiceUnavailableCounter = Counter.build()
            .name("API_Gateway_Request_User_ServiceUnavailable_503")
            .help("Request User ServiceUnavailable 503 Counter").register();


}

package com.shenzhentagram.config;

import com.shenzhentagram.authentication.JWTAuthenticateFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Meranote on 3/6/2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER_URL = "/users";
    private static final String POST_URL = "/posts";
    private static final String COMMENT_URL = "/posts/{post_id}/comments";
    private static final String REACTION_URL = "/posts/{post_id}/reactions";
    private static final String NOTIFICATION_URL = "/notifications";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable caching
        http.headers().cacheControl();

        // Disable csrf
        http.csrf().disable();

        // Authentication Config
        http.authorizeRequests()
                // authorize "/notifications" for
                // (GET) get self notification
                .antMatchers(HttpMethod.GET, NOTIFICATION_URL).authenticated()
                // (PATCH) update notification status
                .antMatchers(HttpMethod.PATCH, NOTIFICATION_URL + "/checked").authenticated()
                .antMatchers(HttpMethod.PATCH, NOTIFICATION_URL + "/unchecked").authenticated()
                // authorize "/posts" for
                // (GET) getting the timeline of self's followings
                .antMatchers(HttpMethod.GET, POST_URL).authenticated()
                // (POST) creating a new post
                .antMatchers(HttpMethod.POST, POST_URL).authenticated()
                // authorize "/posts/{id}" for
                // (PUT) update owns post
                .antMatchers(HttpMethod.PUT, POST_URL + "/{post_id}").authenticated()
                // (DELETE) delete owns post
                .antMatchers(HttpMethod.DELETE, POST_URL + "/{post_id}").authenticated()
                // authorize "/posts/{id}/comments for
                // (POST) create comment
                .antMatchers(HttpMethod.POST, COMMENT_URL).authenticated()
                // (PUT) edit comment
                .antMatchers(HttpMethod.PUT, COMMENT_URL + "/{comment_id}").authenticated()
                // (DELETE) delete comment
                .antMatchers(HttpMethod.DELETE, COMMENT_URL + "/{comment_id}").authenticated()
                // authorize "/posts/{id}/reactions for
                // (POST) create reaction
                .antMatchers(HttpMethod.POST, REACTION_URL).authenticated()
                // (PUT) edit reaction
                .antMatchers(HttpMethod.PUT, REACTION_URL).authenticated()
                // (DELETE) delete reaction
                .antMatchers(HttpMethod.DELETE, REACTION_URL).authenticated()
                // authorize "/users/self" for
                // (GET) get self information
                .antMatchers(HttpMethod.GET, USER_URL + "/self").authenticated()
                // (PATCH) update self information
                .antMatchers(HttpMethod.PATCH, USER_URL + "/self").authenticated()
                // authorize "/users/self/posts" for
                // (GET) Get self posts
                .antMatchers(HttpMethod.GET, USER_URL + "/self/posts").authenticated()

                // Otherwise, permitted
                .anyRequest().permitAll();

        // Filter Config
        // Register JWTAuthenticateFilter for filter any request to check authenticate
        JWTAuthenticateFilter.registerFilter(http);
    }

}

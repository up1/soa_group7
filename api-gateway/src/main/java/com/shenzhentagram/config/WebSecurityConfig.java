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
                .antMatchers(HttpMethod.GET, "notifications").authenticated()
                // (PATCH) update notification status
                .antMatchers(HttpMethod.PATCH, "notifications/status/checked").authenticated()
                .antMatchers(HttpMethod.PATCH, "notifications/status/unchecked").authenticated()
                // authorize "/posts" for
                // (GET) getting the timeline of self's followings
                .antMatchers(HttpMethod.GET, "/posts").authenticated()
                // (POST) creating a new post
                .antMatchers(HttpMethod.POST, "/posts").authenticated()
                // authorize "/posts/{id}" for
                // (PUT) upload owns post
                .antMatchers(HttpMethod.PUT, "/posts/{id}").authenticated()
                // (DELETE) delete owns post
                .antMatchers(HttpMethod.DELETE, "/posts/{id}").authenticated()
                // authorize "/posts/{id}/comments for
                // (POST) create comment
                .antMatchers(HttpMethod.POST, "/posts/{id}/comments").authenticated()
                // authorize "/users/self" for
                // (GET) get self information
                .antMatchers(HttpMethod.GET, "/users/self").authenticated()
                // (PATCH) update self information
                .antMatchers(HttpMethod.PATCH, "/users/self").authenticated()
                // authorize "/users/self/posts" for
                // (GET) Get self posts
                .antMatchers(HttpMethod.GET, "/users/self/posts").authenticated()

                // Otherwise, permitted
                .anyRequest().permitAll();

        // Filter Config
        // Register JWTAuthenticateFilter for filter any request to check authenticate
        JWTAuthenticateFilter.registerFilter(http);
    }

}

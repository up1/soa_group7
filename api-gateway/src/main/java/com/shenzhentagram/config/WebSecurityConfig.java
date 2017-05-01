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

        http.headers().cacheControl();


        http.csrf().disable();


        http.authorizeRequests()

                .antMatchers(HttpMethod.GET, NOTIFICATION_URL).authenticated()

                .antMatchers(HttpMethod.PATCH, NOTIFICATION_URL + "/checked").authenticated()
                .antMatchers(HttpMethod.PATCH, NOTIFICATION_URL + "/unchecked").authenticated()

                .antMatchers(HttpMethod.GET, POST_URL).authenticated()

                .antMatchers(HttpMethod.POST, POST_URL).authenticated()

                .antMatchers(HttpMethod.PUT, POST_URL + "/{post_id}").authenticated()

                .antMatchers(HttpMethod.DELETE, POST_URL + "/{post_id}").authenticated()

                .antMatchers(HttpMethod.POST, COMMENT_URL).authenticated()

                .antMatchers(HttpMethod.PUT, COMMENT_URL + "/{comment_id}").authenticated()

                .antMatchers(HttpMethod.DELETE, COMMENT_URL + "/{comment_id}").authenticated()

                .antMatchers(HttpMethod.POST, REACTION_URL).authenticated()

                .antMatchers(HttpMethod.PUT, REACTION_URL).authenticated()

                .antMatchers(HttpMethod.DELETE, REACTION_URL).authenticated()

                .antMatchers(HttpMethod.POST, USER_URL + "/{id}/follows").authenticated()

                .antMatchers(HttpMethod.DELETE, USER_URL + "/{id}/follows").authenticated()

                .antMatchers(HttpMethod.GET, USER_URL + "/self").authenticated()

                .antMatchers(HttpMethod.PATCH, USER_URL + "/self").authenticated()

                .antMatchers(HttpMethod.GET, USER_URL + "/self/posts").authenticated()

                .anyRequest().permitAll();


        JWTAuthenticateFilter.registerFilter(http);
    }

}

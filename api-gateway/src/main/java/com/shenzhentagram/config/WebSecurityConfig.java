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
                // permit ANY "/"
                .antMatchers("/").permitAll()
                // permit POST "/auth" for authentication
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                // permit POST "/users" for registration
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                // permit GET "/users/search" and "/users/{user_id}" for finding user
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                // Otherwise, need authenticate
                .anyRequest().authenticated();

        // Filter Config
        // Register JWTAuthenticateFilter for filter any request to check authenticate
        JWTAuthenticateFilter.registerFilter(http);
    }

}

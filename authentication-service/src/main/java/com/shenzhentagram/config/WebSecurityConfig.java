package com.shenzhentagram.config;

import com.shenzhentagram.authentication.JWTAuthenticateFilter;
import com.shenzhentagram.filter.JWTLoginFilter;
import com.shenzhentagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Web security configuration
 *
 * @author Meranote
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Config web & mapping filter
     */
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
                // permit POST "/login"
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                // Otherwise, need authenticate
                .anyRequest().authenticated();

        // Filter Config
        // Filter "/auth" for login (before call AuthController@authenticate)
        http.addFilterBefore(new JWTLoginFilter("/auth", authenticationManager(), userService), UsernamePasswordAuthenticationFilter.class);
        // Register JWTAuthenticateFilter for filter any request to check authenticate
        JWTAuthenticateFilter.registerFilter(http);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Config authentication (user queries) => for authenticate
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authenticate from database
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

}

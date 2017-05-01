package com.shenzhentagram.authentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Use in any Service for authorize the authenticated user<br>
 * register this filter via security configuration (add before)
 *
 * @author Meranote
 */
public class JWTAuthenticateFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(((HttpServletRequest) servletRequest).getHeader(JWTAuthenticationService.HEADER_STRING) != null) {
            Authentication authentication = new JWTAuthenticationService().parseToken((HttpServletRequest) servletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static void registerFilter(HttpSecurity http) {
        http.addFilterBefore(new JWTAuthenticateFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}

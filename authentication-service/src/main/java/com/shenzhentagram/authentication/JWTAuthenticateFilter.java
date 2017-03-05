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
 * @author Meranote (chaniwat.meranote@gmail.com)
 */
public class JWTAuthenticateFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO check expired token (renew or reject)
        Authentication authentication = new JWTAuthenticationService().parseToken((HttpServletRequest) servletRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static void registerFilter(HttpSecurity http) {
        http.addFilterBefore(new JWTAuthenticateFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}

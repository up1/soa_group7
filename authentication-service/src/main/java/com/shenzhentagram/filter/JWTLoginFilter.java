package com.shenzhentagram.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.JWTAuthenticationService;
import com.shenzhentagram.model.AccountCredentials;
import com.shenzhentagram.model.User;
import com.shenzhentagram.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

/**
 * Use in Authentication Service for authenticate the user and provide 'access_token'
 *
 * @author Meranote
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    /**
     * Construct new login filter
     * @param url
     * @param authenticationManager
     */
    @Autowired
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager, UserService userService) {
        super(new AntPathRequestMatcher(url, "POST"));
        setAuthenticationManager(authenticationManager);

        this.userService = userService;
    }

    /**
     * Attempt to authenticate
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * If authenticate was successful
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Get successful authenticate user's username
        String username = authResult.getName();

        // Get user by username
        User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Set claims (payloads)
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("role", user.getRole());

        // Build JWT token
        String JWT = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + JWTAuthenticationService.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWTAuthenticationService.SECRET)
                .compact();

        // Set attribute so AuthController@authenticate can response token via JSON
        request.setAttribute("access_token", JWT);
        request.setAttribute("authenticated_user", user);
        // Chain to controller
        chain.doFilter(request, response);
    }

    /**
     * If authenticate was failed, response it
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(401, failed.getMessage());
    }

}

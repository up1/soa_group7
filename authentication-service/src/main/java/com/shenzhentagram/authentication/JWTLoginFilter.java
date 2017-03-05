package com.shenzhentagram.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Use in Authentication Service for authenticate the user and provide 'access_token'
 *
 * @author Meranote (chaniwat.meranote@gmail.com)
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Construct new login filter
     * @param url
     * @param authenticationManager
     */
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Attempt to authenticate
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        // TODO check user from Users-DB

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
        String name = authResult.getName();

        // Build JWT token
        String JWT = Jwts.builder()
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + JWTAuthenticationService.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWTAuthenticationService.SECRET)
                .compact();

        // Set attribute so AuthController@authenticate can response token via JSON
        request.setAttribute("access_token", JWT);
        // Chain to controller
        chain.doFilter(request, response);
    }

    /**
     * If authenticate was failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // TODO Add unsuccessful response message (to AuthController@authenticate => via attribute)
        super.unsuccessfulAuthentication(request, response, failed);
    }

}

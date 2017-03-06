package com.shenzhentagram.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * JWT Configuration class and parse token helper
 *
 * @author Meranote (chaniwat.meranote@gmail.com)
 */
public class JWTAuthenticationService {

    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // in milliseconds, 10 days
    public static final String SECRET = "bWVuaXRl"; // you know :3
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    /**
     * Parse token to authenticated user
     * @param request
     * @return
     */
    public Authentication parseToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if(token != null) {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX + " ", ""));

            // Parse token
            int id = (int) claims.getBody().get("id");
            String username = claims.getBody().getSubject();

            if(username != null) {
               return new AuthenticatedUser(id, username);
            }
        }

        return null;
    }

}

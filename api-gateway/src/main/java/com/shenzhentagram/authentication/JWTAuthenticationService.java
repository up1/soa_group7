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

    public static final long EXPIRATION_TIME = 1000L * 60L * 60L * 24L * 10L;
    public static final String SECRET = "bWVuaXRl";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";


    public Authentication parseToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX + " ", "");

        if(token != null) {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);

            int id = (int) claims.getBody().get("id");
            String username = claims.getBody().getSubject();

            if(username != null) {
               return new AuthenticatedUser(id, username, token);
            }
        }

        return null;
    }

}

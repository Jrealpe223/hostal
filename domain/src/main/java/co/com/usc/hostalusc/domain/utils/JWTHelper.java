package co.com.usc.hostalusc.domain.utils;

import co.com.usc.hostalusc.repository.model.common.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class JWTHelper {
    @Value("${jwt.secret-key}")
    String secretKey;

    public String getJWTToken(String username, User user) {


        String token = Jwts
                .builder()
                .setId("hostaluscJWT")
                .setSubject(username)
                .claim("userId", user.getId().toString())
                .claim("key", "auth0key")
                .claim("isAdmin", user.getAdmin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512)
                .compact();

        return token;
    }
}

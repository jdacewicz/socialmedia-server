package pl.jdacewicz.socialmediaserver.tokengenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class TokenGenerator {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final Key signInKey;

    String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signInKey, SignatureAlgorithm.HS256)
                .compact();
    }
}

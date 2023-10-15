package pl.jdacewicz.socialmediaserver.tokengenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.UserDto;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class TokenGenerator {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final Key signInKey;

    String generateToken(UserDto userDto) {
        return generateToken(new HashMap<>(), userDto);
    }

    String generateToken(Map<String, Object> extraClaims, UserDto userDto) {
        return buildToken(extraClaims, userDto, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDto userDto, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
//                .setSubject(userDto.)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signInKey, SignatureAlgorithm.HS256)
                .compact();
    }
}

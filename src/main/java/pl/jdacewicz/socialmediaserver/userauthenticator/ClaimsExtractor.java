package pl.jdacewicz.socialmediaserver.userauthenticator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
class ClaimsExtractor {

    private final Key signInKey;

    String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

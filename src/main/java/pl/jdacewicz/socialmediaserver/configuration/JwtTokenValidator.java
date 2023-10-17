package pl.jdacewicz.socialmediaserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@RequiredArgsConstructor
class JwtTokenValidator {

    private final JwtClaimsExtractor jwtClaimsExtractor;

    boolean isValid(String jwtToken, UserDetails userDetails) {
        if (jwtToken == null) {
            return false;
        }
        if (isTokenDataAndProvidedDataNotEqual(jwtToken, userDetails)) {
            return false;
        }
        if (isTokenExpired(jwtToken)) {
            return false;
        }
        return true;
    }

    private boolean isTokenDataAndProvidedDataNotEqual(String jwtToken, UserDetails userDetails) {
        var extractedUsername = jwtClaimsExtractor.extractUsername(jwtToken);
        var providedUsername = userDetails.getUsername();
        return !extractedUsername.equals(providedUsername);
    }

    private boolean isTokenExpired(String jwtToken) {
        var extractedExpiration = jwtClaimsExtractor.extractExpiration(jwtToken);
        var currentDate = new Date();
        return extractedExpiration.before(currentDate);
    }
}

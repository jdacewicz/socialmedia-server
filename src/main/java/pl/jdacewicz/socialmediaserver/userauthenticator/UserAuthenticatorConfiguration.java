package pl.jdacewicz.socialmediaserver.userauthenticator;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;

import java.security.Key;

class UserAuthenticatorConfiguration {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService, TokenGeneratorFacade tokenGeneratorFacade) {
        var claimsExtractor = new ClaimsExtractor(getSignInKey());
        return new JwtAuthenticationFilter(userDetailsService, claimsExtractor, tokenGeneratorFacade);
    }

    private Key getSignInKey() {
        var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

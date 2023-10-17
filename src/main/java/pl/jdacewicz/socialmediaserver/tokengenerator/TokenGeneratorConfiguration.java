package pl.jdacewicz.socialmediaserver.tokengenerator;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
class TokenGeneratorConfiguration {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Bean
    TokenGenerator tokenGenerator() {
        return new TokenGenerator(getSignInKey());
    }

    @Bean
    TokenGeneratorFacade tokenGeneratorFacade(TokenGeneratorService tokenGeneratorService) {
        return new TokenGeneratorFacade(tokenGeneratorService);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

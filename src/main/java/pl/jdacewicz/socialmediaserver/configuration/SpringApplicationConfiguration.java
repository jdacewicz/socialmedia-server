package pl.jdacewicz.socialmediaserver.configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;

import java.security.Key;

@Configuration
public class SpringApplicationConfiguration {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    JwtClaimsExtractor jwtClaimsExtractorFacade() {
        return new JwtClaimsExtractor(getSignInKey());
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService,
                                                    TokenGeneratorFacade tokenGeneratorFacade,
                                                    JwtClaimsExtractor jwtClaimsExtractor) {
        var jwtTokenValidator = new JwtTokenValidator(jwtClaimsExtractor);
        return new JwtAuthenticationFilter(userDetailsService, tokenGeneratorFacade, jwtClaimsExtractor,
                jwtTokenValidator);
    }

    private Key getSignInKey() {
        var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

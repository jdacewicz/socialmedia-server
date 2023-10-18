package pl.jdacewicz.socialmediaserver.userauthenticator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserAuthenticatorConfiguration {

    @Bean
    UserAuthenticatorFacade userAuthenticatorFacade(UserAuthenticatorService userAuthenticatorService) {
        return new UserAuthenticatorFacade(userAuthenticatorService);
    }
}

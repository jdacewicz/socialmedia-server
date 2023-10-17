package pl.jdacewicz.socialmediaserver.userauthenticator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Configuration
class UserAuthenticatorConfiguration {

    @Bean
    UserAuthenticatorFacade userAuthenticatorFacade(AuthenticationManager authenticationManager,
                                                    UserDataReceiverFacade userDataReceiverFacade,
                                                    TokenGeneratorFacade tokenGeneratorFacade) {
        return new UserAuthenticatorFacade(authenticationManager, userDataReceiverFacade,
                tokenGeneratorFacade);
    }
}

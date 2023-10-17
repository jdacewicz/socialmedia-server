package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserDataReceiverConfiguration {

    @Bean
    UserDataReceiverFacade userDataReceiverFacade(UserDataReceiverService userDataReceiverService) {
        return new UserDataReceiverFacade(userDataReceiverService);
    }
}

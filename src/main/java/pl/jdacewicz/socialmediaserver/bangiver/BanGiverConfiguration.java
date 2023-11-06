package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Configuration
class BanGiverConfiguration {

    @Bean
    BanGiverFacade banGiverFacade(BanGiverService banGiverService,
                                  TemporaryBanGiverService temporaryBanGiverService,
                                  UserDataReceiverFacade userDataReceiverFacade) {
        return new BanGiverFacade(banGiverService, temporaryBanGiverService, userDataReceiverFacade);
    }
}

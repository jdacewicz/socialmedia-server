package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.configuration.JwtClaimsExtractor;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;

@Configuration
class UserDataReceiverConfiguration {

    @Bean
    UserDataReceiverFacade userDataReceiverFacade(UserDataReceiverService userDataReceiverService,
                                                  UserMapper userMapper,
                                                  FileStorageFacade fileStorageFacade,
                                                  JwtClaimsExtractor jwtClaimsExtractor) {
        return new UserDataReceiverFacade(userDataReceiverService, userMapper, fileStorageFacade, jwtClaimsExtractor);
    }
}

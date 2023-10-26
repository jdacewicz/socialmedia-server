package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;

@Configuration
class ReactionDataReceiverConfiguration {

    @Bean
    ReactionDataReceiverFacade reactionDataReceiverFacade(ReactionDataReceiverService reactionDataReceiverService,
                                                          FileStorageFacade fileStorageFacade) {
        return new ReactionDataReceiverFacade(reactionDataReceiverService, fileStorageFacade);
    }
}

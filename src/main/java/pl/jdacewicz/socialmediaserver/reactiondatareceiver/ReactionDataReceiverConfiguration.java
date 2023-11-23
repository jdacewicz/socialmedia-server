package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;

@Configuration
class ReactionDataReceiverConfiguration {

    @Bean
    ReactionDataReceiverFacade reactionDataReceiverFacade(ReactionDataReceiverService reactionDataReceiverService,
                                                          FileStorageFacade fileStorageFacade,
                                                          FileMapperFacade fileMapperFacade) {
        return new ReactionDataReceiverFacade(reactionDataReceiverService, fileStorageFacade, fileMapperFacade);
    }
}

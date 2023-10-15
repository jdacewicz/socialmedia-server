package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactionDataReceiverConfiguration {

    @Bean
    ReactionDataReceiverFacade reactionDataReceiverFacade(ReactionDataReceiverService reactionDataReceiverService) {
        return new ReactionDataReceiverFacade(reactionDataReceiverService);
    }
}

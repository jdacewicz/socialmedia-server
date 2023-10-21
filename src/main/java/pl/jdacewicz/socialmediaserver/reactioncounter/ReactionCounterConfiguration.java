package pl.jdacewicz.socialmediaserver.reactioncounter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactionCounterConfiguration {

    @Bean
    ReactionCounterFacade reactionCounterFacade() {
        var reactionCounter = new ReactionCounter();
        return new ReactionCounterFacade(reactionCounter);
    }
}

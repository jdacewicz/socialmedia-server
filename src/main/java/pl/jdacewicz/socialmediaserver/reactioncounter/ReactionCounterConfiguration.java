package pl.jdacewicz.socialmediaserver.reactioncounter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactionCounterConfiguration {

    @Bean
    ReactionCounter reactionCounter() {
        return new ReactionCounter();
    }

    @Bean
    ReactionCounterFacade reactionCounterFacade(ReactionCounterService reactionCounterService) {
        return new ReactionCounterFacade(reactionCounterService);
    }
}

package pl.jdacewicz.socialmediaserver.reactionuserpreparer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactionUserPreparerConfiguration {

    @Bean
    ReactionUserPreparerFacade reactionUserPreparerFacade(ReactionUserPreparerService reactionUserPreparerService) {
        return new ReactionUserPreparerFacade(reactionUserPreparerService);
    }
}

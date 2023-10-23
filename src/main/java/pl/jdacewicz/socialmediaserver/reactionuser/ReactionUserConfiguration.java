package pl.jdacewicz.socialmediaserver.reactionuser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactionUserConfiguration {

    @Bean
    ReactionUserFacade reactionUserFacade(ReactionUserService reactionUserService) {
        return new ReactionUserFacade(reactionUserService);
    }
}

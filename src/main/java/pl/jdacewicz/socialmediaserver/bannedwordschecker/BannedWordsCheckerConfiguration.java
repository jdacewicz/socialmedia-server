package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BannedWordsCheckerConfiguration {

    @Bean
    BannedWordsCheckerFacade bannedWordsCheckerFacade(BannedWordService bannedWordService,
                                                      BannedWordMapper bannedWordMapper) {
        return new BannedWordsCheckerFacade(bannedWordService, bannedWordMapper);
    }
}

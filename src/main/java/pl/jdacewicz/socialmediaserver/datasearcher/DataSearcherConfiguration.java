package pl.jdacewicz.socialmediaserver.datasearcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Configuration
class DataSearcherConfiguration {

    @Bean
    DataSearcherFacade dataSearcherFacade(SearchResultFactory searchResultFactory) {
        return new DataSearcherFacade(searchResultFactory);
    }

    @Bean
    SearchResultFactory searchResultFactory(UserDataReceiverFacade userDataReceiverFacade,
                                            DiscussionDataReceiverFacade discussionDataReceiverFacade,
                                            BannedWordsCheckerFacade bannedWordsCheckerFacade,
                                            ReactionDataReceiverFacade reactionDataReceiverFacade) {
        var defaultSearchStrategy = new DefaultSearchStrategy(userDataReceiverFacade, discussionDataReceiverFacade,
                bannedWordsCheckerFacade, reactionDataReceiverFacade);
        return new SearchResultFactory(defaultSearchStrategy);
    }
}

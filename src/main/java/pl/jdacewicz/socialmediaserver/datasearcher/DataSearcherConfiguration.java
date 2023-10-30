package pl.jdacewicz.socialmediaserver.datasearcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Configuration
class DataSearcherConfiguration {

    @Bean
    DataSearcherFacade dataSearcherFacade(UserDataReceiverFacade userDataReceiverFacade,
                                          DiscussionDataReceiverFacade discussionDataReceiverFacade) {
        DefaultSearchStrategy defaultSearchStrategy = new DefaultSearchStrategy(userDataReceiverFacade,
                discussionDataReceiverFacade);
        return new DataSearcherFacade(defaultSearchStrategy);
    }
}
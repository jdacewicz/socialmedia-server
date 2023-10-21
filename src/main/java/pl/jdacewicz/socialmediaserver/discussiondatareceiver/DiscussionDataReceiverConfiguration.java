package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DiscussionDataReceiverConfiguration {

    @Bean
    DiscussionDataReceiverFacade dataReceiverFacade(DiscussionDataReceiverService discussionDataReceiverService,
                                                    PostMapper postMapper) {
        return new DiscussionDataReceiverFacade(discussionDataReceiverService, postMapper);
    }
}

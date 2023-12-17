package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.ReactionUserFacade;

@Configuration
class DiscussionDataReceiverConfiguration {

    @Bean
    DiscussionDataReceiverFacade dataReceiverFacade(DiscussionDataReceiverFactoryImpl discussionDataReceiverFactoryImpl,
                                                    ReactionUserFacade reactionUserFacade,
                                                    FileStorageFacade fileStorageFacade,
                                                    DiscussionMapper discussionMapper) {
        return new DiscussionDataReceiverFacade(discussionDataReceiverFactoryImpl, reactionUserFacade, fileStorageFacade,
                discussionMapper);
    }
}

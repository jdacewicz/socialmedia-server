package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.ReactionUserFacade;

@Configuration
class DiscussionDataReceiverConfiguration {

    @Bean
    DiscussionDataReceiverFacade dataReceiverFacade(DiscussionDataReceiverService discussionDataReceiverService,
                                                    ReactionUserFacade reactionUserFacade,
                                                    FileStorageFacade fileStorageFacade,
                                                    PostMapper postMapper,
                                                    CommentMapper commentMapper) {
        return new DiscussionDataReceiverFacade(discussionDataReceiverService, reactionUserFacade,
                fileStorageFacade, postMapper, commentMapper);
    }
}

package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionCreationRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

interface DiscussionDataReceiverService<T> {

    T getDiscussionById(String id);

    Page<T> getDiscussionsByContentContaining(String phrase, Pageable pageable);

    <S extends DiscussionCreationRequest> T createDiscussion(String authenticationHeader, String imageName, S request);

    T reactToDiscussionById(String id, ReactionUser reactionUser);

    void deleteDiscussionById(String id);

    void deleteAllDiscussions();
}

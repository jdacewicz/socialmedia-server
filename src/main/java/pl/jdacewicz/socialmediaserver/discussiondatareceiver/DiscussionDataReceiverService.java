package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

abstract class DiscussionDataReceiverService<T extends Discussion<T>> {

    abstract T getDiscussionById(String id);

    abstract Page<T> getDiscussionsByContentContaining(String phrase, Pageable pageable);

    abstract T reactToDiscussionById(String id, ReactionUser reactionUser);

    abstract void deleteDiscussion(T discussion);


    abstract void deleteAllDiscussions();

    T react(T discussion, ReactionUser reactionUser) {
        if (discussion.isReactionUserStored(reactionUser)) {
            return discussion.withoutReactionUser(reactionUser);
        }
        return discussion.withReactionUser(reactionUser);
    }
}

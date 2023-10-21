package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.ReactionUserPreparerFacade;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUserRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DiscussionDataReceiverService {

    private final DiscussionDataReceiverRepository discussionDataReceiverRepository;
    private final ReactionUserPreparerFacade reactionUserPreparerFacade;

    Post getPostById(String id) {
        return discussionDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Post createPost(PostRequest postRequest) {
        var post = Post.builder()
                .content(postRequest.content())
                .creationDateTime(LocalDateTime.now())
                .build();
        return discussionDataReceiverRepository.save(post);
    }

    @Transactional
    public Post reactToPost(PostReactionRequest postReactionRequest) {
        var reactionUser = getReactionUser(postReactionRequest);
        var post = getPostById(postReactionRequest.postId());
        post.reactionUsers()
                .add(reactionUser);
        return discussionDataReceiverRepository.save(post);
    }

    private ReactionUser getReactionUser(PostReactionRequest postReactionRequest) {
        var reactionUserRequest = ReactionUserRequest.builder()
                .reactionId(postReactionRequest.reactionId())
                .build();
        return reactionUserPreparerFacade.prepareReactionUser(reactionUserRequest);
    }
}

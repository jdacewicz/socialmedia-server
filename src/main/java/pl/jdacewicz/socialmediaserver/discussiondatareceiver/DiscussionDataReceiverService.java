package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DiscussionDataReceiverService {

    private final DiscussionDataReceiverRepository discussionDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    Post getPostById(String id) {
        return discussionDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    public Post createPost(String content, String imageName) {
        var loggedInUser = userDataReceiverFacade.getLoggedInUser();
        var post = Post.builder()
                .content(content)
                .creator(loggedInUser)
                .creationDateTime(LocalDateTime.now())
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.profilePicture()
                        .folderDirectory())
                .build();
        return discussionDataReceiverRepository.save(post);
    }

    @Transactional
    public Post reactToPostById(String postId, ReactionUser reactionUser) {
        var post = getPostById(postId);
        if (post.isReactionUserStored(reactionUser)) {
            post = post.withoutReactionUser(reactionUser);
        } else {
            post = post.withReactionUser(reactionUser);
        }
        return discussionDataReceiverRepository.save(post);
    }

    void deletePost(Post post) {
        discussionDataReceiverRepository.delete(post);
    }
}

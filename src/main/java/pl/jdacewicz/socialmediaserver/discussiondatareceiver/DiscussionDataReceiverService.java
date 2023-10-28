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

    private final PostDataReceiverRepository postDataReceiverRepository;
    private final CommentDataReceiverRepository commentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    Post getPostById(String postId) {
        return postDataReceiverRepository.findById(postId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Comment getCommentById(String commentId) {
        return commentDataReceiverRepository.findById(commentId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Post createPost(String content, String imageName) {
        var loggedInUser = userDataReceiverFacade.getLoggedInUser();
        var post = Post.builder()
                .content(content)
                .creator(loggedInUser)
                .creationDateTime(LocalDateTime.now())
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.profilePicture()
                        .folderDirectory())
                .build();
        return postDataReceiverRepository.save(post);
    }

    @Transactional
    public Post reactToPostById(String postId, ReactionUser reactionUser) {
        var post = getPostById(postId);
        post = react(post, reactionUser);
        return postDataReceiverRepository.save(post);
    }

    void deletePost(Post post) {
        postDataReceiverRepository.delete(post);
    }

    void deleteComment(Comment comment) {
        commentDataReceiverRepository.delete(comment);
    }

    private  <T extends Discussion<T>> T react(T discussion, ReactionUser reactionUser) {
        return (discussion.isReactionUserStored(reactionUser))
                ? discussion.withoutReactionUser(reactionUser)
                : discussion.withReactionUser(reactionUser);
    }
}

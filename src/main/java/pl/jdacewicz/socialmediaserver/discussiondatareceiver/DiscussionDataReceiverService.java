package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

@Service
@RequiredArgsConstructor
class DiscussionDataReceiverService {

    private final PostDataReceiverRepository postDataReceiverRepository;
    private final CommentDataReceiverRepository commentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Transactional
    public Comment createComment(String postId, String content, String imageName) {
        var loggedUser = userDataReceiverFacade.getLoggedInUser();
        var foundPost = getPostById(postId);
        var comment = commentPost(content, imageName, loggedUser, foundPost);
        return commentDataReceiverRepository.save(comment);
    }

    @Transactional
    public Post reactToPostById(String postId, ReactionUser reactionUser) {
        var post = getPostById(postId);
        post = react(post, reactionUser);
        return postDataReceiverRepository.save(post);
    }

    @Transactional
    public Comment reactToCommentById(String commentId, ReactionUser reactionUser) {
        var comment = getCommentById(commentId);
        comment = react(comment, reactionUser);
        return commentDataReceiverRepository.save(comment);
    }

    Post getPostById(String postId) {
        return postDataReceiverRepository.findById(postId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Comment getCommentById(String commentId) {
        return commentDataReceiverRepository.findById(commentId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Set<Post> getPostsByContentContaining(String phrase) {
        return postDataReceiverRepository.findByContentContaining(phrase);
    }

    Set<Comment> getCommentsByContentContaining(String phrase) {
        return commentDataReceiverRepository.findByContentContaining(phrase);
    }

    Post createPost(String content, String imageName) {
        var loggedInUser = userDataReceiverFacade.getLoggedInUser();
        var post = Post.builder()
                .content(content)
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.profilePicture()
                        .folderDirectory())
                .build();
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

    private Comment commentPost(String content, String imageName, UserDto loggedUser, Post foundPost) {
        var comment = Comment.builder()
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var commentedPost = foundPost.withComment(comment);
        postDataReceiverRepository.save(commentedPost);
        return comment;
    }
}

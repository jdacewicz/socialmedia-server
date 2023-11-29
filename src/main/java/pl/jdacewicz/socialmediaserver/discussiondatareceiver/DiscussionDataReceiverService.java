package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
class DiscussionDataReceiverService {

    @Value("${posts.random.count}")
    private int randomPostsCount;

    private final PostDataReceiverRepository postDataReceiverRepository;
    private final CommentDataReceiverRepository commentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public Comment createComment(String postId, String content, String imageName, String authenticationHeader) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var foundPost = getPostById(postId);
        var comment = Comment.builder()
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var createdComment = commentDataReceiverRepository.save(comment);
        postDataReceiverRepository.save(foundPost.withComment(createdComment));
        return createdComment;
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

    List<Post> getRandomPosts() {
        var aggregation = Aggregation.newAggregation(
                Aggregation.sample(randomPostsCount));
        return mongoTemplate.aggregate(aggregation, "posts", Post.class)
                .getMappedResults();
    }

    List<Post> getPostsByUserId(String userId) {
        return postDataReceiverRepository.findAllByCreator_UserId(userId);
    }

    Set<Post> getPostsByContentContaining(String phrase) {
        return postDataReceiverRepository.findByContentContaining(phrase);
    }

    Set<Comment> getCommentsByContentContaining(String phrase) {
        return commentDataReceiverRepository.findByContentContaining(phrase);
    }

    Post createPost(String content, String jwtToken, String imageName) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedInUser = userDataReceiverFacade.getLoggedInUser(jwtToken);
        var post = Post.builder()
                .content(content)
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.getDataDirectory())
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

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllPosts() {
        postDataReceiverRepository.deleteAll();
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllComments() {
        commentDataReceiverRepository.deleteAll();
    }
}

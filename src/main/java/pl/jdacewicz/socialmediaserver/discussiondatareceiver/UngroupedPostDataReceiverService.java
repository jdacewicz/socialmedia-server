package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class UngroupedPostDataReceiverService extends DiscussionDataReceiverService<Post> implements PostDataReceiverService<Post> {

    @Value("${posts.random.count}")
    private int randomPostsCount;

    private final PostDataReceiverRepository postDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Post> getRandomPosts() {
        var aggregation = Aggregation.newAggregation(
                Aggregation.sample(randomPostsCount));
        return mongoTemplate.aggregate(aggregation, "posts", Post.class)
                .getMappedResults();
    }

    @Override
    public Page<Post> getPostsByCreatorUserId(String userId, Pageable pageable) {
        return postDataReceiverRepository.findAllByCreator_UserId(userId, pageable);
    }

    @Override
    public Post createPost(String content, String authenticationHeader, String imageName) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedInUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var post = Post.builder()
                .content(content)
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.getDataDirectory())
                .build();
        return postDataReceiverRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        postDataReceiverRepository.save(post);
    }

    @Override
    @Transactional
    public Post reactToDiscussionById(String id, ReactionUser reactionUser) {
        var post = getDiscussionById(id);
        post = react(post, reactionUser);
        return postDataReceiverRepository.save(post);
    }

    @Override
    Post getDiscussionById(String id) {
        return postDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    Page<Post> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return postDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    void deleteDiscussion(Post discussion) {
        postDataReceiverRepository.delete(discussion);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllDiscussions() {
        postDataReceiverRepository.deleteAll();
    }
}

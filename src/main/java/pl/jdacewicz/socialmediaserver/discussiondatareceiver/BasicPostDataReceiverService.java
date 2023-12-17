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
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionCreationRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class BasicPostDataReceiverService implements PostDataReceiverService<BasicPost> {

    @Value("${posts.random.count}")
    private int randomPostsCount;

    private final BasicPostDataReceiverRepository basicPostDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;
    private final MongoTemplate mongoTemplate;

    @Override
    public BasicPost getDiscussionById(String id) {
        return basicPostDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public List<BasicPost> getRandomPosts() {
        var aggregation = Aggregation.newAggregation(
                Aggregation.sample(randomPostsCount));
        return mongoTemplate.aggregate(aggregation, "posts", BasicPost.class)
                .getMappedResults();
    }

    @Override
    public Page<BasicPost> getPostsByCreatorUserId(String userId, Pageable pageable) {
        return basicPostDataReceiverRepository.findAllByCreator_UserId(userId, pageable);
    }

    @Override
    public Page<BasicPost> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return basicPostDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    public <S extends DiscussionCreationRequest> BasicPost createDiscussion(String authenticationHeader, String imageName,
                                                                            S request) {
        bannedWordsCheckerFacade.checkForBannedWords(request.getContent());
        var loggedInUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var post = BasicPost.builder()
                .content(request.getContent())
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.getDataDirectory())
                .build();
        return basicPostDataReceiverRepository.save(post);
    }

    @Override
    public void updatePost(BasicPost post) {
        basicPostDataReceiverRepository.save(post);
    }

    @Override
    @Transactional
    public BasicPost reactToDiscussionById(String id, ReactionUser reactionUser) {
        var post = getDiscussionById(id);
        if (post.isReactionUserStored(reactionUser)) {
            return basicPostDataReceiverRepository.save(post.withoutReactionUser(reactionUser));
        }
        return basicPostDataReceiverRepository.save(post.withReactionUser(reactionUser));
    }

    @Override
    public void deleteDiscussionById(String id) {
        basicPostDataReceiverRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    public void deleteAllDiscussions() {
        basicPostDataReceiverRepository.deleteAll();
    }
}

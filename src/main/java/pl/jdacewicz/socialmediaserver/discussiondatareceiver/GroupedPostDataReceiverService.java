package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
class GroupedPostDataReceiverService implements PostDataReceiverService<GroupedPost> {

    @Value("${posts.random.count}")
    private int randomPostsCount;

    private final GroupedPostDataReceiverRepository groupedPostDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;
    private final MongoTemplate mongoTemplate;

    @Override
    public GroupedPost getDiscussionById(String id) {
        return groupedPostDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public List<GroupedPost> getRandomPosts() {
        var aggregation = Aggregation.newAggregation(
                Aggregation.sample(randomPostsCount));
        return mongoTemplate.aggregate(aggregation, "grouped_posts", GroupedPost.class)
                .getMappedResults();
    }

    @Override
    public Page<GroupedPost> getPostsByCreatorUserId(String userId, Pageable pageable) {
        return groupedPostDataReceiverRepository.findAllByCreator_UserId(userId, pageable);
    }

    @Override
    public Page<GroupedPost> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return groupedPostDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    public void updatePost(GroupedPost post) {
        groupedPostDataReceiverRepository.save(post);
    }

    @Override
    public GroupedPost reactToDiscussionById(String id, ReactionUser reactionUser) {
        var post = getDiscussionById(id);
        if (post.isReactionUserStored(reactionUser)) {
            return groupedPostDataReceiverRepository.save(post.withoutReactionUser(reactionUser));
        }
        return groupedPostDataReceiverRepository.save(post.withReactionUser(reactionUser));
    }

    @Override
    public void deleteDiscussionById(String id) {
        groupedPostDataReceiverRepository.deleteById(id);
    }

    @Override
    public void deleteAllDiscussions() {
        groupedPostDataReceiverRepository.deleteAll();
    }

    GroupedPost createGroupedPost(String groupId, String imageName, String authenticationHeader, PostRequest postRequest) {
        GroupedPost post;
        LoggedUserDto loggedInUser;
        bannedWordsCheckerFacade.checkForBannedWords(postRequest.content());
        loggedInUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        post = GroupedPost.builder()
                .groupId(groupId)
                .content(postRequest.content())
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.getDataDirectory())
                .build();
        return groupedPostDataReceiverRepository.save(post);
    }
}

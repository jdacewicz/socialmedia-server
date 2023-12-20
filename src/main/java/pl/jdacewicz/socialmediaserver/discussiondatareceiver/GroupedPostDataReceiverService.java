package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionCreationRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.GroupedPostCreationRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Comment> getCommentsByPostId(String postId, int commentQuantity) {
        var post = getDiscussionById(postId);
        return post.getComments()
                .stream()
                .limit(commentQuantity)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<GroupedPost> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return groupedPostDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    public <S extends DiscussionCreationRequest> GroupedPost createDiscussion(String authenticationHeader, String imageName, S request) {
        var groupedPostRequest = (GroupedPostCreationRequest) request;
        bannedWordsCheckerFacade.checkForBannedWords(groupedPostRequest.getContent());
        var loggedInUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var post = GroupedPost.builder()
                .groupId(groupedPostRequest.getGroupId())
                .content(groupedPostRequest.getContent())
                .creator(loggedInUser)
                .imageName(imageName)
                .imageMainDirectory(loggedInUser.getDataDirectory())
                .build();
        return groupedPostDataReceiverRepository.save(post);
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
}

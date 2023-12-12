package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GroupedPostDataReceiverServiceTest {

    GroupedPostDataReceiverService groupedPostDataReceiverService;

    GroupedPostDataReceiverRepositoryTest groupedPostDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        groupedPostDataReceiverRepositoryTest = new GroupedPostDataReceiverRepositoryTest();
        groupedPostDataReceiverService = new GroupedPostDataReceiverService(groupedPostDataReceiverRepositoryTest,
                userDataReceiverFacade, bannedWordsCheckerFacade, mongoTemplate);
    }

    @Test
    void should_return_grouped_post_when_getting_discussion_by_existing_id() {
        //Given
        var discussionId = "id";
        var groupedPost = GroupedPost.builder()
                .discussionId(discussionId)
                .build();
        groupedPostDataReceiverRepositoryTest.save(groupedPost);
        //When
        var result = groupedPostDataReceiverService.getDiscussionById(discussionId);
        //Then
        assertEquals(discussionId, result.getDiscussionId());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_not_existing_grouped_post() {
        //Given
        var discussionId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> groupedPostDataReceiverService.getDiscussionById(discussionId));
    }

    @Test
    void should_return_created_grouped_post_when_creating_grouped_post() {
        var groupId = "id";
        var imageName = "image.png";
        var authenticationHeader = "header";
        var postRequest = new PostRequest("content");
        var loggedUser = LoggedUserDto.builder()
                .build();
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        //When
        var result = groupedPostDataReceiverService.createGroupedPost(groupId, imageName, authenticationHeader, postRequest);
        //Then
        assertEquals(groupId, result.getGroupId());
        assertEquals(postRequest.content(), result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUser, result.getCreator());
    }

    @Test
    void should_return_grouped_post_without_reaction_user_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var groupedPost = GroupedPost.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of(reactionUser))
                .build();
        groupedPostDataReceiverRepositoryTest.save(groupedPost);
        //When
        var result = groupedPostDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_grouped_post_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var groupedPost = GroupedPost.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of())
                .build();
        groupedPostDataReceiverRepositoryTest.save(groupedPost);
        //When
        var result = groupedPostDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
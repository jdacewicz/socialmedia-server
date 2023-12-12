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

class BasicPostDataReceiverServiceTest {

    BasicPostDataReceiverService basicPostDataReceiverService;

    BasicPostDataReceiverRepositoryTest basicPostDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        basicPostDataReceiverRepositoryTest = new BasicPostDataReceiverRepositoryTest();
        basicPostDataReceiverService = new BasicPostDataReceiverService(basicPostDataReceiverRepositoryTest,
                userDataReceiverFacade, bannedWordsCheckerFacade, mongoTemplate);
    }

    @Test
    void should_return_basic_post_when_getting_discussion_by_existing_id() {
        //Given
        var discussionId = "id";
        var basicPost = BasicPost.builder()
                .discussionId(discussionId)
                .build();
        basicPostDataReceiverRepositoryTest.save(basicPost);
        //When
        var result = basicPostDataReceiverService.getDiscussionById(discussionId);
        //Then
        assertEquals(discussionId, result.getDiscussionId());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_not_existing_basic_post() {
        //Given
        var discussionId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> basicPostDataReceiverService.getDiscussionById(discussionId));
    }

    @Test
    void should_return_created_basic_post_when_creating_basic_post() {
        var imageName = "image.png";
        var authenticationHeader = "header";
        var postRequest = new PostRequest("content");
        var loggedUser = LoggedUserDto.builder()
                .build();
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        //When
        var result = basicPostDataReceiverService.createBasicPost(authenticationHeader, imageName, postRequest);
        //Then
        assertEquals(postRequest.content(), result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUser, result.getCreator());
    }

    @Test
    void should_return_basic_post_without_reaction_user_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var basicPost = BasicPost.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of(reactionUser))
                .build();
        basicPostDataReceiverRepositoryTest.save(basicPost);
        //When
        var result = basicPostDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_basic_post_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var basicPost = BasicPost.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of())
                .build();
        basicPostDataReceiverRepositoryTest.save(basicPost);
        //When
        var result = basicPostDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
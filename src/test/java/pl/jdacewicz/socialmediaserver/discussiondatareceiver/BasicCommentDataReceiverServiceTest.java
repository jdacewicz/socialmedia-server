package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BasicCommentDataReceiverServiceTest {

    BasicCommentDataReceiverService basicCommentDataReceiverService;

    BasicPostDataReceiverService basicPostDataReceiverService;
    BasicCommentDataReceiverRepositoryTest basicCommentDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @BeforeEach
    void setUp() {
        basicPostDataReceiverService = Mockito.mock(BasicPostDataReceiverService.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        basicCommentDataReceiverRepositoryTest = new BasicCommentDataReceiverRepositoryTest();
        basicCommentDataReceiverService = new BasicCommentDataReceiverService(basicPostDataReceiverService,
                basicCommentDataReceiverRepositoryTest, userDataReceiverFacade, bannedWordsCheckerFacade);
    }

    @Test
    void should_return_basic_comment_when_getting_discussion_by_existing_id() {
        //Given
        var discussionId = "id";
        var basicComment = BasicComment.builder()
                .discussionId(discussionId)
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = basicCommentDataReceiverService.getDiscussionById(discussionId);
        //Then
        assertEquals(discussionId, result.getDiscussionId());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_not_existing_basic_comment() {
        //Given
        var discussionId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> basicCommentDataReceiverService.getDiscussionById(discussionId));
    }

    @Test
    void should_return_created_comment_when_creating_comment() {
        //Given
        var postId = "id";
        var content = "content";
        var imageName = "image.png";
        var authenticationHeader = "header";
        var loggedUser = LoggedUserDto.builder()
                .build();
        var foundPost = BasicPost.builder()
                .discussionId(postId)
                .build();
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        when(basicPostDataReceiverService.getDiscussionById(postId)).thenReturn(foundPost);
        //When
        var result = basicCommentDataReceiverService.createBasicComment(postId, content, imageName, authenticationHeader);
        //Then
        assertEquals(content, result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUser, result.getCreator());
    }

    @Test
    void should_return_basic_comment_without_reaction_user_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var basicComment = BasicComment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of(reactionUser))
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = basicCommentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_basic_comment_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var basicComment = BasicComment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of())
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = basicCommentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
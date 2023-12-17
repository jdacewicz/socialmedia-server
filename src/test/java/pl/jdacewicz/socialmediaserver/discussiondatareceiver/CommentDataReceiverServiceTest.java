package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentCreationRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommentDataReceiverServiceTest {

    CommentDataReceiverService commentDataReceiverService;

    BasicPostDataReceiverService basicPostDataReceiverService;
    CommentDataReceiverRepositoryTest basicCommentDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @BeforeEach
    void setUp() {
        basicPostDataReceiverService = Mockito.mock(BasicPostDataReceiverService.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        basicCommentDataReceiverRepositoryTest = new CommentDataReceiverRepositoryTest();
        commentDataReceiverService = new CommentDataReceiverService(basicPostDataReceiverService,
                basicCommentDataReceiverRepositoryTest, userDataReceiverFacade, bannedWordsCheckerFacade);
    }

    @Test
    void should_return_basic_comment_when_getting_discussion_by_existing_id() {
        //Given
        var discussionId = "id";
        var basicComment = Comment.builder()
                .discussionId(discussionId)
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = commentDataReceiverService.getDiscussionById(discussionId);
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
                () -> commentDataReceiverService.getDiscussionById(discussionId));
    }

    @Test
    void should_return_created_comment_when_creating_comment() {
        //Given
        var postId = "id";
        var imageName = "image.png";
        var authenticationHeader = "header";
        var loggedUser = LoggedUserDto.builder()
                .build();
        var foundPost = BasicPost.builder()
                .discussionId(postId)
                .build();
        var request = CommentCreationRequest.builder()
                .postId(postId)
                .content("content")
                .build();
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        when(basicPostDataReceiverService.getDiscussionById(postId)).thenReturn(foundPost);
        //When
        var result = commentDataReceiverService.createDiscussion(authenticationHeader, imageName, request);
        //Then
        assertEquals(request.getContent(), result.getContent());
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
        var basicComment = Comment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of(reactionUser))
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = commentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
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
        var basicComment = Comment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of())
                .build();
        basicCommentDataReceiverRepositoryTest.save(basicComment);
        //When
        var result = commentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
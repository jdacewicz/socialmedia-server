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

class GroupedCommentDataReceiverServiceTest {

    GroupedCommentDataReceiverService groupedCommentDataReceiverService;

    GroupedPostDataReceiverService groupedPostDataReceiverService;
    GroupedCommentDataReceiverRepositoryTest groupedCommentDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @BeforeEach
    void setUp() {
        groupedPostDataReceiverService = Mockito.mock(GroupedPostDataReceiverService.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        groupedCommentDataReceiverRepositoryTest = new GroupedCommentDataReceiverRepositoryTest();
        groupedCommentDataReceiverService = new GroupedCommentDataReceiverService(groupedCommentDataReceiverRepositoryTest,
                groupedPostDataReceiverService, userDataReceiverFacade, bannedWordsCheckerFacade);
    }

    @Test
    void should_return_grouped_comment_when_getting_discussion_by_existing_id() {
        //Given
        var discussionId = "id";
        var groupedComment = GroupedComment.builder()
                .discussionId(discussionId)
                .build();
        groupedCommentDataReceiverRepositoryTest.save(groupedComment);
        //When
        var result = groupedCommentDataReceiverService.getDiscussionById(discussionId);
        //Then
        assertEquals(discussionId, result.getDiscussionId());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_not_existing_grouped_comment() {
        //Given
        var discussionId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> groupedCommentDataReceiverService.getDiscussionById(discussionId));
    }

    @Test
    void should_return_grouped_comment_when_creating_comment() {
        //Given
        var postId = "id";
        var groupId = "id";
        var content = "content";
        var imageName = "image.png";
        var authenticationHeader = "header";
        var loggedUser = LoggedUserDto.builder()
                .build();
        var foundPost = GroupedPost.builder()
                .discussionId(postId)
                .build();
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        when(groupedPostDataReceiverService.getDiscussionById(postId)).thenReturn(foundPost);
        //When
        var result = groupedCommentDataReceiverService.createGroupedComment(postId, groupId, content,
                imageName, authenticationHeader);
        //Then
        assertEquals(groupId, result.getGroupId());
        assertEquals(content, result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUser, result.getCreator());
    }

    @Test
    void should_return_grouped_comment_without_reaction_user_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var groupedComment = GroupedComment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of(reactionUser))
                .build();
        groupedCommentDataReceiverRepositoryTest.save(groupedComment);
        //When
        var result = groupedCommentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_grouped_comment_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var discussionId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var groupedComment = GroupedComment.builder()
                .discussionId(discussionId)
                .reactionUsers(List.of())
                .build();
        groupedCommentDataReceiverRepositoryTest.save(groupedComment);
        //When
        var result = groupedCommentDataReceiverService.reactToDiscussionById(discussionId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
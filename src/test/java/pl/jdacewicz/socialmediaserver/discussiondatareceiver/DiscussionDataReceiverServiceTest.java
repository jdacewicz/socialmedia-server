package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DiscussionDataReceiverServiceTest {

    DiscussionDataReceiverService discussionDataReceiverService;

    PostDataReceiverRepositoryTest postDataReceiverRepositoryTest;
    CommentDataReceiverRepositoryTest commentDataReceiverRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        postDataReceiverRepositoryTest = new PostDataReceiverRepositoryTest();
        commentDataReceiverRepositoryTest = new CommentDataReceiverRepositoryTest();
        discussionDataReceiverService = new DiscussionDataReceiverService(postDataReceiverRepositoryTest,
                commentDataReceiverRepositoryTest, userDataReceiverFacade, bannedWordsCheckerFacade, mongoTemplate);
    }

    @Test
    public void should_return_post_when_getting_existing_post() {
        //Given
        var postId = "id";
        var post = Post.builder()
                .postId(postId)
                .build();
        postDataReceiverRepositoryTest.save(post);
        //When
        var result = discussionDataReceiverService.getPostById(postId);
        //Then
        assertEquals(postId, result.getPostId());
    }

    @Test
    public void should_throw_unsupported_operation_exception_when_getting_not_existing_post() {
        //Given
        var postId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> discussionDataReceiverService.getPostById(postId));
    }

    @Test
    public void should_return_comment_when_getting_existing_comment() {
        //Given
        var commentId = "id";
        var comment = Comment.builder()
                .commentId(commentId)
                .build();
        commentDataReceiverRepositoryTest.save(comment);
        //When
        var result = discussionDataReceiverService.getCommentById(commentId);
        //Then
        assertEquals(commentId, result.getCommentId());
    }

    @Test
    public void should_throw_unsupported_operation_exception_when_getting_not_existing_comment() {
        //Given
        var commentId = "id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> discussionDataReceiverService.getCommentById(commentId));
    }

    @Test
    void should_return_created_post_when_creating_post() {
        //Given
        var content = "content";
        var imageName = "name";
        var loggedUserDto = LoggedUserDto.builder()
                .profilePicture(new File("url"))
                .build();
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUserDto);
        //When
        var result = discussionDataReceiverService.createPost(content, imageName);
        //Then
        assertEquals(content, result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUserDto, result.getCreator());
    }

    @Test
    void should_return_created_comment_when_creating_comment() {
        //Given
        var content = "content";
        var imageName = "name";
        var postId = "id";
        var post = Post.builder()
                .postId(postId)
                .build();
        var loggedUserDto = LoggedUserDto.builder()
                .profilePicture(new File("url"))
                .build();
        postDataReceiverRepositoryTest.save(post);
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUserDto);
        //When
        var result = discussionDataReceiverService.createComment(postId, content, imageName);
        //Then
        assertEquals(content, result.getContent());
        assertEquals(imageName, result.getImageName());
        assertEquals(loggedUserDto, result.getCreator());
    }

    @Test
    void should_return_post_without_reactions_users_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var postId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var post = Post.builder()
                .postId(postId)
                .reactionUsers(List.of(reactionUser))
                .build();
        postDataReceiverRepositoryTest.save(post);
        //When
        var result = discussionDataReceiverService.reactToPostById(postId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_post_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var postId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var post = Post.builder()
                .postId(postId)
                .reactionUsers(List.of())
                .build();
        postDataReceiverRepositoryTest.save(post);
        //When
        var result = discussionDataReceiverService.reactToPostById(postId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }

    @Test
    void should_return_comment_without_reactions_users_when_reacting_by_already_reacted_reaction_user() {
        //Given
        var commentId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var comment = Comment.builder()
                .commentId(commentId)
                .reactionUsers(List.of(reactionUser))
                .build();
        commentDataReceiverRepositoryTest.save(comment);
        //When
        var result = discussionDataReceiverService.reactToCommentById(commentId, reactionUser);
        //Then
        assertTrue(result.getReactionUsers()
                .isEmpty());
    }

    @Test
    void should_return_comment_with_reaction_user_when_reacting_by_unique_reaction_user() {
        //Given
        var commentId = "id";
        var reactionUser = ReactionUser.builder()
                .userId("id")
                .reactionId("id")
                .build();
        var comment = Comment.builder()
                .commentId(commentId)
                .reactionUsers(List.of())
                .build();
        commentDataReceiverRepositoryTest.save(comment);
        //When
        var result = discussionDataReceiverService.reactToCommentById(commentId, reactionUser);
        //Then
        assertFalse(result.getReactionUsers()
                .isEmpty());
        assertEquals(1, result.getReactionUsers()
                .size());
    }
}
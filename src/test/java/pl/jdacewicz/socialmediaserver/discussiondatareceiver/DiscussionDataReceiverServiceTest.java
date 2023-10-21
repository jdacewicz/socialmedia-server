package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.ReactionUserPreparerFacade;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DiscussionDataReceiverServiceTest {

    DiscussionDataReceiverService discussionDataReceiverService;

    DiscussionDataReceiverRepositoryTest discussionDataReceiverRepository;
    ReactionUserPreparerFacade reactionUserPreparerFacade;

    @BeforeEach
    void setUp() {
        reactionUserPreparerFacade = Mockito.mock(ReactionUserPreparerFacade.class);
        discussionDataReceiverRepository = new DiscussionDataReceiverRepositoryTest();
        discussionDataReceiverService = new DiscussionDataReceiverService(discussionDataReceiverRepository,
                reactionUserPreparerFacade);
    }

    @Test
    public void should_return_post_when_getting_existing_post() {
        //Given
        var postId = "id";
        var post = Post.builder()
                .postId(postId)
                .build();
        discussionDataReceiverRepository.save(post);
        //When
        var result = discussionDataReceiverService.getPostById(postId);
        //Then
        assertEquals(postId, result.postId());
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
    void should_return_created_post_when_creating_post() {
        //Given
        var postRequest = new PostRequest("content");
        //When
        var result = discussionDataReceiverService.createPost(postRequest);
        //Then
        assertEquals(postRequest.content(), result.content());
    }

    @Test
    void should_return_reacted_post_reacting_to_post() {
        //Given
        var postReactionRequest = PostReactionRequest.builder()
                .reactionId("id")
                .postId("id")
                .build();
        var reactionUser = ReactionUser.builder()
                .build();
        var post = Post.builder()
                .postId(postReactionRequest.postId())
                .build();
        when(reactionUserPreparerFacade.prepareReactionUser(any())).thenReturn(reactionUser);
        discussionDataReceiverRepository.save(post);
        //When
        var result = discussionDataReceiverService.reactToPost(postReactionRequest);
        //Then
        assertFalse(result.reactionUsers()
                .isEmpty());
        assertEquals(reactionUser, result.reactionUsers()
                .get(0));
    }

}
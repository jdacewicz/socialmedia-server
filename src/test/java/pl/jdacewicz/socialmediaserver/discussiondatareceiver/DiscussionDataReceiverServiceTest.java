package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

import static org.junit.jupiter.api.Assertions.*;

class DiscussionDataReceiverServiceTest {

    DiscussionDataReceiverService discussionDataReceiverService;

    DiscussionDataReceiverRepositoryTest discussionDataReceiverRepository;

    @BeforeEach
    void setUp() {
        discussionDataReceiverRepository = new DiscussionDataReceiverRepositoryTest();
        discussionDataReceiverService = new DiscussionDataReceiverService(discussionDataReceiverRepository);
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

}
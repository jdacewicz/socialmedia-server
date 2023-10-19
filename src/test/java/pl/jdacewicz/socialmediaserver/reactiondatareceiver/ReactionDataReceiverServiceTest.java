package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

import static org.junit.jupiter.api.Assertions.*;

class ReactionDataReceiverServiceTest {

    ReactionDataReceiverService reactionDataReceiverService;

    ReactionDataReceiverRepositoryTest reactionDataReceiverRepository;

    @BeforeEach
    void setUp() {
        reactionDataReceiverRepository = new ReactionDataReceiverRepositoryTest();
        reactionDataReceiverService = new ReactionDataReceiverService(reactionDataReceiverRepository);
    }

    @Test
    void should_return_created_reaction_when_creating_reaction() {
        //Given
        var reactionRequest = new ReactionRequest("name");
        //When
        var result = reactionDataReceiverService.createReaction(reactionRequest);
        //Then
        assertEquals(reactionRequest.name(), result.name());
    }
}
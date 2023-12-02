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
    void should_return_reaction_when_reaction_exist() {
        //Given
        var id = "reactionId";
        var reaction = Reaction.builder()
                .reactionId(id)
                .build();
        reactionDataReceiverRepository.save(reaction);
        //When
        var result = reactionDataReceiverService.getReactionById(id);
        //Then
        assertEquals(id, result.reactionId());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_user_doesnt_exist() {
        //Given
        var id = "reactionId";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> reactionDataReceiverService.getReactionById(id));
    }

    @Test
    void should_return_created_reaction_when_creating_reaction() {
        //Given
        var reactionRequest = new ReactionRequest("name");
        var imageFileName = "file.png";
        //When
        var result = reactionDataReceiverService.createReaction(reactionRequest, imageFileName);
        //Then
        assertEquals(reactionRequest.name(), result.name());
    }
}
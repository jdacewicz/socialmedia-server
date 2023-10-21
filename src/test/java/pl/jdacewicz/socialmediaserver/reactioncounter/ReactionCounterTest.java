package pl.jdacewicz.socialmediaserver.reactioncounter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReactionCounterTest {

    ReactionCounter reactionCounter;

    @BeforeEach
    void setUp() {
        reactionCounter = new ReactionCounter();
    }

    @Test
    void should_return_proper_count_when_counting_all_reactions() {
        //Given
        var reactionDto = ReactionDto.builder()
                .reactionId("id")
                .build();
        var reactionUser = ReactionUser.builder()
                .reaction(reactionDto)
                .build();
        var reactionUserTwo = ReactionUser.builder()
                .reaction(reactionDto)
                .build();
        var reactionUsers = List.of(reactionUser, reactionUserTwo);
        //When
        var result = reactionCounter.countAllReactions(reactionUsers);
        //Then
        assertFalse(result.isEmpty());
        assertEquals(2, result.get(reactionDto));
    }
}
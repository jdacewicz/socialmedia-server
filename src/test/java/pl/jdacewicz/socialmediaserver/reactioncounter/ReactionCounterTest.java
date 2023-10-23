package pl.jdacewicz.socialmediaserver.reactioncounter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReactionCounterTest {

    ReactionCounter reactionCounter;

    @BeforeEach
    void setUp() {
        reactionCounter = new ReactionCounter();
    }

    @Test
    void should_return_proper_counts_when_counting_all_reactions_by_active_reactions() {
        //Given
        var activeReactionDto = ReactionDto.builder()
                .reactionId("id")
                .build();
        var reactionUser = ReactionUser.builder()
                .reactionId(activeReactionDto.reactionId())
                .build();
        var reactionUserTwo = ReactionUser.builder()
                .reactionId("other id")
                .build();
        var activeReactions = List.of(activeReactionDto);
        var reactionUsers = List.of(reactionUser, reactionUserTwo);
        //When
        var result = reactionCounter.countReactionsByActiveReactions(reactionUsers, activeReactions);
        //Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1, result.get(activeReactionDto));
    }
}
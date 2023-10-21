package pl.jdacewicz.socialmediaserver.reactionuserpreparer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUserRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReactionUserPreparerServiceTest {

    ReactionUserPreparerService reactionUserPreparerService;

    ReactionDataReceiverFacade reactionDataReceiverFacade;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        reactionDataReceiverFacade = Mockito.mock(ReactionDataReceiverFacade.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        reactionUserPreparerService = new ReactionUserPreparerService(reactionDataReceiverFacade, userDataReceiverFacade);
    }

    @Test
    void should_return_reaction_user_when_preparing_reaction_user() {
        //Given
        var reactionUserRequest = ReactionUserRequest.builder()
                .reactionId("id")
                .build();
        var reactionDto = ReactionDto.builder()
                .reactionId(reactionUserRequest.reactionId())
                .build();
        var userDto = UserDto.builder().build();
        when(reactionDataReceiverFacade.getReactionById(reactionUserRequest.reactionId()))
                .thenReturn(reactionDto);
        when(userDataReceiverFacade.getLoggedInUser())
                .thenReturn(userDto);
        //When
        var result = reactionUserPreparerService.prepareReactionUser(reactionUserRequest);
        //Then
        assertEquals(reactionDto, result.reaction());
        assertEquals(userDto, result.reactor());
    }

}
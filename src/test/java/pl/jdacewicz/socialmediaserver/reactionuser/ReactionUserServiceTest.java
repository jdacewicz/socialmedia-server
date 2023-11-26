package pl.jdacewicz.socialmediaserver.reactionuser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReactionUserServiceTest {

    ReactionUserService reactionUserService;

    ReactionDataReceiverFacade reactionDataReceiverFacade;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        reactionDataReceiverFacade = Mockito.mock(ReactionDataReceiverFacade.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        reactionUserService = new ReactionUserService(reactionDataReceiverFacade, userDataReceiverFacade);
    }

    @Test
    void should_return_reactionuser_when_creating_reactionuser_by_reactionuserrequest() {
        //Given
        var reactionId = "id";
        var userId = "id";
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionDto = ReactionDto.builder()
                .reactionId(reactionId)
                .build();
        var userDto = LoggedUserDto.builder()
                .userId(userId)
                .build();
        var authenticationHeader = "token";
        when(reactionDataReceiverFacade.getReactionById(reactionId)).thenReturn(reactionDto);
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(userDto);
        //When
        var result = reactionUserService.createReactionUser(authenticationHeader, reactionUserRequest);
        //Then
        assertEquals(reactionId, result.reactionId());
        assertEquals(userId, result.userId());
    }

}
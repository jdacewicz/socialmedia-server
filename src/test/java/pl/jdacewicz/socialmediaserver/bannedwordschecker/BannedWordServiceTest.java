package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BannedWordServiceTest {

    BannedWordService bannedWordService;

    BannedWordRepositoryTest bannedWordRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        bannedWordRepositoryTest = new BannedWordRepositoryTest();
        bannedWordService = new BannedWordService(bannedWordRepositoryTest, userDataReceiverFacade);
    }

    @Test
    void should_return_created_banned_word_when_creating_banned_word() {
        //Given
        var banWordRequest = new BanWordRequest("word");
        var loggedUser = LoggedUserDto.builder()
                .userId("id")
                .build();
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUser);
        //When
        var result = bannedWordService.createBannedWord(banWordRequest);
        //Then
        assertEquals(loggedUser.getUserId(), result.creatorId());
        assertEquals(banWordRequest.word(), result.word());
    }
}
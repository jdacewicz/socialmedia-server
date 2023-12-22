package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BanWordRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void should_return_banned_word_when_getting_banned_word_by_banned_word() {
        //Given
        var word = "word";
        var bannedWord = BannedWord.builder()
                .word(word)
                .build();
        bannedWordRepositoryTest.save(bannedWord);
        //When
        var result = bannedWordService.getBannedWordByWord(word);
        //Then
        assertEquals(word, result.word());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_banned_word_by_not_banned_word() {
        //Given
        var word = "word";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> bannedWordService.getBannedWordByWord(word));
    }

    @Test
    void should_return_created_banned_word_when_creating_banned_word() {
        //Given
        var banWordRequest = new BanWordRequest("word");
        var loggedUser = LoggedUserDto.builder()
                .userId("id")
                .build();
        var authenticationHeader = "token";
        when(userDataReceiverFacade.getLoggedInUser(authenticationHeader)).thenReturn(loggedUser);
        //When
        var result = bannedWordService.createBannedWord(authenticationHeader, banWordRequest);
        //Then
        assertEquals(loggedUser.getUserId(), result.creatorId());
        assertEquals(banWordRequest.word(), result.word());
    }
}
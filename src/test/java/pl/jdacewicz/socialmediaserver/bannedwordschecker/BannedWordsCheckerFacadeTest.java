package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BannedWordsCheckerFacadeTest {

    BannedWordsCheckerFacade bannedWordsCheckerFacade;

    BannedWordService bannedWordService;
    BannedWordMapper bannedWordMapper;

    @BeforeEach
    void setUp() {
        bannedWordService = Mockito.mock(BannedWordService.class);
        bannedWordMapper = Mockito.mock(BannedWordMapper.class);
        bannedWordsCheckerFacade = new BannedWordsCheckerFacade(bannedWordService, bannedWordMapper);
    }

    @Test
    void should_throw_unsupported_operation_exception_when_checking_for_banned_words_by_text_with_banned_words() {
        //Given
        var text = "text";
        var bannedWord = BannedWord.builder()
                .word(text)
                .build();
        when(bannedWordService.getAllBannedWords()).thenReturn(List.of(bannedWord));
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> bannedWordsCheckerFacade.checkForBannedWords(text));
    }

    @Test
    void should_not_throw_unsupported_operation_exception_when_checking_for_banned_words_by_text_without_banned_words() {
        //Given
        var text = "text";
        when(bannedWordService.getAllBannedWords()).thenReturn(List.of());
        //When
        //Then
        assertDoesNotThrow(() -> bannedWordsCheckerFacade.checkForBannedWords(text));
    }
}
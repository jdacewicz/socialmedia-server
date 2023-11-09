package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BannedWordsCheckerTest {

    @Test
    void should_return_true_when_checking_does_text_contain_banned_words_by_banned_words_and_text_containing_banned_words() {
        //Given
        var textToCheck = "text to check";
        var bannedWord = BannedWord.builder()
                .word("check")
                .build();
        var bannedWords = List.of(bannedWord);
        //When
        var result = BannedWordsChecker.doesTextContainBannedWords(textToCheck, bannedWords);
        //Then
        assertTrue(result);
    }

    @Test
    void should_return_false_when_checking_does_text_contain_banned_words_by_banned_words_and_text_not_containing_banned_words() {
        //Given
        var textToCheck = "text";
        var bannedWord = BannedWord.builder()
                .word("check")
                .build();
        var bannedWords = List.of(bannedWord);
        //When
        var result = BannedWordsChecker.doesTextContainBannedWords(textToCheck, bannedWords);
        //Then
        assertFalse(result);
    }
}
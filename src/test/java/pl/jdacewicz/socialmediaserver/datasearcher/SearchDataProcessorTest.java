package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchDataProcessorTest {

    @Test
    void should_return_words_when_splitting_text_to_unique_words_by_unique_words() {
        //Given
        var words = "word1 word2";
        //When
        var result = SearchDataProcessor.splitTextToUniqueWords(words);
        //Then
        assertEquals(2, result.size());
    }

    @Test
    void should_return_word_when_splitting_text_to_unique_words_by_not_unique_words() {
        //Given
        var words = "word1 word1";
        //When
        var result = SearchDataProcessor.splitTextToUniqueWords(words);
        //Then
        assertEquals(1, result.size());
    }
}
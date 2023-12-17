package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchResultFactoryTest {

    SearchResultFactory searchResultFactory;

    SearchStrategy searchStrategy;

    @BeforeEach
    void setUp() {
        searchStrategy = Mockito.mock(SearchStrategy.class);
        searchResultFactory = new SearchResultFactory(searchStrategy);
    }

    @Test
    void should_return_all_when_scope_equals_all() {
        //Given
        var scope = "all";
        var phrase = "test";
        var searchResult = SearchResult.builder()
                .build();
        var pageNumber = 0;
        var pageSize = 1;
        when(searchStrategy.searchAll(phrase, pageNumber, pageSize)).thenReturn(searchResult);
        //When
        var result = searchResultFactory.searchData(scope, pageNumber, pageSize, phrase);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchAll(phrase, pageNumber, pageSize);
    }

    @Test
    void should_return_users_when_scope_equals_users() {
        //Given
        var scope = "users";
        var phrase = "test";
        var searchResult = SearchResult.builder()
                .build();
        var pageNumber = 0;
        var pageSize = 1;
        when(searchStrategy.searchUsers(phrase, pageNumber, pageSize)).thenReturn(searchResult);
        //When
        var result = searchResultFactory.searchData(scope, pageNumber, pageSize, phrase);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchUsers(phrase, pageNumber, pageSize);
    }

    @Test
    void should_return_posts_when_scope_equals_posts() {
        //Given
        var scope = "posts";
        var phrase = "test";
        var searchResult = SearchResult.builder()
                .build();
        var pageNumber = 0;
        var pageSize = 1;
        when(searchStrategy.searchPosts(phrase, pageNumber, pageSize)).thenReturn(searchResult);
        //When
        var result = searchResultFactory.searchData(scope, pageNumber, pageSize, phrase);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchPosts(phrase, pageNumber, pageSize);
    }
}
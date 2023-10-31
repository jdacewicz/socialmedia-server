package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DataSearcherFacadeTest {

    DataSearcherFacade dataSearcherFacade;

    SearchStrategy searchStrategy;

    @BeforeEach
    void setUp() {
        searchStrategy = Mockito.mock(SearchStrategy.class);
        dataSearcherFacade = new DataSearcherFacade(searchStrategy);
    }

    @Test
    void should_return_all_when_scope_equals_all() {
        //Given
        var scope = "all";
        var searchRequest = new SearchRequest("test");
        var searchResult = SearchResult.builder()
                .build();
        when(searchStrategy.searchAll(searchRequest)).thenReturn(searchResult);
        //When
        var result = dataSearcherFacade.searchData(scope, searchRequest);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchAll(searchRequest);
    }

    @Test
    void should_return_users_when_scope_equals_users() {
        //Given
        var scope = "users";
        var searchRequest = new SearchRequest("test");
        var searchResult = SearchResult.builder()
                .build();
        when(searchStrategy.searchUsers(searchRequest)).thenReturn(searchResult);
        //When
        var result = dataSearcherFacade.searchData(scope, searchRequest);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchUsers(searchRequest);
    }

    @Test
    void should_return_posts_when_scope_equals_posts() {
        //Given
        var scope = "posts";
        var searchRequest = new SearchRequest("test");
        var searchResult = SearchResult.builder()
                .build();
        when(searchStrategy.searchPosts(searchRequest)).thenReturn(searchResult);
        //When
        var result = dataSearcherFacade.searchData(scope, searchRequest);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchPosts(searchRequest);
    }

    @Test
    void should_return_comments_when_scope_equals_comments() {
        //Given
        var scope = "comments";
        var searchRequest = new SearchRequest("test");
        var searchResult = SearchResult.builder()
                .build();
        when(searchStrategy.searchComments(searchRequest)).thenReturn(searchResult);
        //When
        var result = dataSearcherFacade.searchData(scope, searchRequest);
        //Then
        assertEquals(searchResult, result);
        verify(searchStrategy, times(1)).searchComments(searchRequest);
    }
}
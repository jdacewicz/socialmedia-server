package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchRequestScopeTest {

    @Test
    void should_return_all_when_getting_scope_by_all_string() {
        //Given
        var scope = "all";
        //When
        var result = SearchRequestScope.getScope(scope);
        //Then
        assertEquals(SearchRequestScope.ALL, result);
    }

    @Test
    void should_return_users_when_getting_scope_by_users_string() {
        //Given
        var scope = "users";
        //When
        var result = SearchRequestScope.getScope(scope);
        //Then
        assertEquals(SearchRequestScope.USERS, result);
    }

    @Test
    void should_return_posts_when_getting_scope_by_posts_string() {
        //Given
        var scope = "posts";
        //When
        var result = SearchRequestScope.getScope(scope);
        //Then
        assertEquals(SearchRequestScope.POSTS, result);
    }

    @Test
    void should_throw_illegal_argument_exception_when_getting_scope_by_unrecognized_string() {
        //Given
        var scope = "unrecognized";
        //When
        //Then
        assertThrows(IllegalArgumentException.class,
                () -> SearchRequestScope.getScope(scope));
    }
}
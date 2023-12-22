package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.AllArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@AllArgsConstructor
class SearchResultFactory {

    private final SearchStrategy searchStrategy;

    public SearchResult searchData(String scope, int pageNumber, int pageSize, String phrase) {
        var searchRequestScope = SearchRequestScope.getScope(scope);
        return switch (searchRequestScope) {
            case ALL -> searchStrategy.searchAll(phrase, pageNumber, pageSize);
            case USERS -> searchStrategy.searchUsers(phrase, pageNumber, pageSize);
            case POSTS -> searchStrategy.searchPosts(phrase, pageNumber, pageSize);
            case BANNED_WORDS -> searchStrategy.searchBannedWords(phrase, pageNumber, pageSize);
            case REACTIONS ->  searchStrategy.searchReactions(phrase, pageNumber, pageSize);
        };
    }
}

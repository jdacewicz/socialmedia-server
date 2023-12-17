package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.AllArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@AllArgsConstructor
class SearchResultFactory {

    private final SearchStrategy searchStrategy;

    public SearchResult searchData(String scope, int pageNumber, int pageSize, SearchRequest searchRequest) {
        var searchRequestScope = SearchRequestScope.getScope(scope);
        return switch (searchRequestScope) {
            case ALL -> searchStrategy.searchAll(searchRequest, pageNumber, pageSize);
            case USERS -> searchStrategy.searchUsers(searchRequest, pageNumber, pageSize);
            case POSTS -> searchStrategy.searchPosts(searchRequest, pageNumber, pageSize);
        };
    }
}

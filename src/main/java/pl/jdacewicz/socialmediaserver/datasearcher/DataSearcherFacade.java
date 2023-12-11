package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RequiredArgsConstructor
public class DataSearcherFacade {

    private final SearchStrategy searchStrategy;

    public SearchResult searchData(String scope, int pageNumber, int pageSize, SearchRequest searchRequest) {
        var searchRequestScope = SearchRequestScope.getScope(scope);
        return switch (searchRequestScope) {
            case ALL -> searchStrategy.searchAll(searchRequest, pageNumber, pageSize);
            case USERS -> searchStrategy.searchUsers(searchRequest, pageNumber, pageSize);
            case POSTS -> searchStrategy.searchPosts(searchRequest, pageNumber, pageSize);
            case COMMENTS -> searchStrategy.searchComments(searchRequest, pageNumber, pageSize);
        };
    }
}

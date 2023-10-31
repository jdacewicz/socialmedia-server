package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RequiredArgsConstructor
public class DataSearcherFacade {

    private final SearchStrategy searchStrategy;

    public SearchResult searchData(String scope, SearchRequest searchRequest) {
        var searchRequestScope = SearchRequestScope.getScope(scope);
        return switch (searchRequestScope) {
            case ALL -> searchStrategy.searchAll(searchRequest);
            case USERS -> searchStrategy.searchUsers(searchRequest);
            case POSTS -> searchStrategy.searchPosts(searchRequest);
            case COMMENTS -> searchStrategy.searchComments(searchRequest);
        };
    }
}

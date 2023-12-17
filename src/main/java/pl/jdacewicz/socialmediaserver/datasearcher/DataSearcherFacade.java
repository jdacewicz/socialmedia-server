package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RequiredArgsConstructor
public class DataSearcherFacade {

    private final SearchResultFactory searchResultFactory;

    public SearchResult searchData(String scope, int pageNumber, int pageSize, SearchRequest searchRequest) {
        return searchResultFactory.searchData(scope, pageNumber, pageSize, searchRequest);
    }
}

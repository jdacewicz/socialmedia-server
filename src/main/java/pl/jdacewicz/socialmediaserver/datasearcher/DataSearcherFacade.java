package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

@RequiredArgsConstructor
public class DataSearcherFacade {

    private final SearchResultFactory searchResultFactory;

    public SearchResult searchData(String scope, int pageNumber, int pageSize, String phrase) {
        return searchResultFactory.searchData(scope, pageNumber, pageSize, phrase);
    }
}

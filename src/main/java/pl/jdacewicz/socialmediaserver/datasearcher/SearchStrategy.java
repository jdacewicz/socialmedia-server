package pl.jdacewicz.socialmediaserver.datasearcher;

import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

interface SearchStrategy {

    SearchResult searchAll(SearchRequest searchRequest, int pageNumber, int pageSize);

    SearchResult searchUsers(SearchRequest searchRequest, int pageNumber, int pageSize);

    SearchResult searchPosts(SearchRequest searchRequest, int pageNumber, int pageSize);
}

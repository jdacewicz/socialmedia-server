package pl.jdacewicz.socialmediaserver.datasearcher;

import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

interface SearchStrategy {

    SearchResult searchAll(SearchRequest searchRequest);
    SearchResult searchUsers(SearchRequest searchRequest);
    SearchResult searchPosts(SearchRequest searchRequest);
    SearchResult searchComments(SearchRequest searchRequest);
}

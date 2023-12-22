package pl.jdacewicz.socialmediaserver.datasearcher;

import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;

interface SearchStrategy {

    SearchResult searchAll(String phrase, int pageNumber, int pageSize);

    SearchResult searchUsers(String phrase, int pageNumber, int pageSize);

    SearchResult searchPosts(String phrase, int pageNumber, int pageSize);

    SearchResult searchBannedWords(String phrase, int pageNumber, int pageSize);

    SearchResult searchReactions(String phrase, int pageNumber, int pageSize);
}

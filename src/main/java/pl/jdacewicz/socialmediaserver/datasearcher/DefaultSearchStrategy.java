package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@RequiredArgsConstructor
class DefaultSearchStrategy implements SearchStrategy {

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    private final String type = "BASIC";

    @Override
    public SearchResult searchAll(SearchRequest searchRequest, int pageNumber, int pageSize) {
        var uniqueWords = SearchDataProcessor.splitTextToUniqueWords(searchRequest.phrase());
        var foundPosts = discussionDataReceiverFacade.getDiscussionsByContentContaining(searchRequest.phrase(), type, pageNumber, pageSize);
        var foundComments = discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.phrase(), type, pageNumber, pageSize);
        var foundUsers = userDataReceiverFacade.getUsersByFirstnamesAndLastnames(uniqueWords, uniqueWords, pageNumber, pageSize);
        return SearchResult.builder()
                .posts(foundPosts)
                .comments(foundComments)
                .users(foundUsers)
                .build();
    }

    @Override
    public SearchResult searchUsers(SearchRequest searchRequest, int pageNumber, int pageSize) {
        var uniqueWords = SearchDataProcessor.splitTextToUniqueWords(searchRequest.phrase());
        var foundUsers =  userDataReceiverFacade.getUsersByFirstnamesAndLastnames(uniqueWords, uniqueWords, pageNumber, pageSize);
        return SearchResult.builder()
                .users(foundUsers)
                .build();
    }

    @Override
    public SearchResult searchPosts(SearchRequest searchRequest, int pageNumber, int pageSize) {
        var foundPosts = discussionDataReceiverFacade.getDiscussionsByContentContaining(searchRequest.phrase(), type, pageNumber, pageSize);
        return SearchResult.builder()
                .posts(foundPosts)
                .build();
    }

    @Override
    public SearchResult searchComments(SearchRequest searchRequest, int pageNumber, int pageSize) {
        var comments = discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.phrase(), type, pageNumber, pageSize);
        return SearchResult.builder()
                .comments(comments)
                .build();
    }
}

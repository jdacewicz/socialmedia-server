package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

@RequiredArgsConstructor
class DefaultSearchStrategy implements SearchStrategy {

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @Override
    public SearchResult searchAll(SearchRequest searchRequest) {
        var foundPosts = discussionDataReceiverFacade.getPostsByContentContaining(searchRequest.typedInText());
        var foundComments = discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.typedInText());
        var foundUsers = searchUsers(searchRequest.typedInText());
        return SearchResult.builder()
                .posts(foundPosts)
                .comments(foundComments)
                .users(foundUsers)
                .build();
    }

    @Override
    public SearchResult searchUsers(SearchRequest searchRequest) {
        var foundUsers = searchUsers(searchRequest.typedInText());
        return SearchResult.builder()
                .users(foundUsers)
                .build();
    }

    @Override
    public SearchResult searchPosts(SearchRequest searchRequest) {
        var foundPosts = discussionDataReceiverFacade.getPostsByContentContaining(searchRequest.typedInText());
        return SearchResult.builder()
                .posts(foundPosts)
                .build();
    }

    @Override
    public SearchResult searchComments(SearchRequest searchRequest) {
        var foundComments = discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.typedInText());
        return SearchResult.builder()
                .comments(foundComments)
                .build();
    }

    private Set<UserDto> searchUsers(String typedInText) {
        var uniqueWords = SearchDataProcessor.splitTextToUniqueWords(typedInText);
        return userDataReceiverFacade.getUsersByFirstnamesAndLastnames(uniqueWords, uniqueWords);
    }
}

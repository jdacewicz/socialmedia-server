package pl.jdacewicz.socialmediaserver.datasearcher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchResult;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@RequiredArgsConstructor
class DefaultSearchStrategy implements SearchStrategy {

    final static String postSearchType = "BASIC_POST";

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @Override
    public SearchResult searchAll(String phrase, int pageNumber, int pageSize) {
        var foundUsers = getUserDtosByUniqueWords(phrase, pageNumber, pageSize);
        var foundPosts = discussionDataReceiverFacade.getDiscussionsByContentContaining(phrase, postSearchType, pageNumber, pageSize);
        return SearchResult.builder()
                .users(foundUsers)
                .posts(foundPosts)
                .build();
    }

    @Override
    public SearchResult searchUsers(String phrase, int pageNumber, int pageSize) {
        var foundUsers = getUserDtosByUniqueWords(phrase, pageNumber, pageSize);
        return SearchResult.builder()
                .users(foundUsers)
                .build();
    }

    @Override
    public SearchResult searchPosts(String phrase, int pageNumber, int pageSize) {
        var foundPosts = discussionDataReceiverFacade.getDiscussionsByContentContaining(phrase, postSearchType, pageNumber, pageSize);
        return SearchResult.builder()
                .posts(foundPosts)
                .build();
    }

    private Page<UserDto> getUserDtosByUniqueWords(String phrase, int pageNumber, int pageSize) {
        var uniqueWords = SearchDataProcessor.splitTextToUniqueWords(phrase);
        return userDataReceiverFacade.getUsersByFirstnamesAndLastnames(uniqueWords, uniqueWords, pageNumber, pageSize);
    }
}

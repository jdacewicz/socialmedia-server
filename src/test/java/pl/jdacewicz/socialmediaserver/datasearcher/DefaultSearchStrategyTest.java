package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.dto.BannedWordDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class DefaultSearchStrategyTest {

    final String dataType = DefaultSearchStrategy.postSearchType;
    final String phrase = "test";
    final int pageNumber = 0;
    final int pageSize = 1;

    DefaultSearchStrategy defaultSearchStrategy;

    UserDataReceiverFacade userDataReceiverFacade;
    DiscussionDataReceiverFacade discussionDataReceiverFacade;
    BannedWordsCheckerFacade bannedWordsCheckerFacade;
    ReactionDataReceiverFacade reactionDataReceiverFacade;


    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        discussionDataReceiverFacade = Mockito.mock(DiscussionDataReceiverFacade.class);
        bannedWordsCheckerFacade = Mockito.mock(BannedWordsCheckerFacade.class);
        reactionDataReceiverFacade = Mockito.mock(ReactionDataReceiverFacade.class);
        defaultSearchStrategy = new DefaultSearchStrategy(userDataReceiverFacade, discussionDataReceiverFacade,
                bannedWordsCheckerFacade, reactionDataReceiverFacade);
    }

    @Test
    void should_return_search_result_with_users_and_posts_when_searching_all() {
        //Given
        var postDto = DiscussionDto.builder()
                .build();
        var userDto = UserDto.builder()
                .build();
        var posts = new PageImpl<>(List.of(postDto));
        var users = new PageImpl<>(List.of(userDto));
        when(discussionDataReceiverFacade.getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize))
                .thenReturn(posts);
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(Set.of(phrase), Set.of(phrase), pageNumber, pageSize))
                .thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchAll(phrase, pageNumber, pageSize);
        //Then
        assertFalse(result.results().get("users")
                .isEmpty());
        assertFalse(result.results().get("posts")
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize);
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(Set.of(phrase),
                Set.of(phrase), pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_users_when_searching_users() {
        //Given
        var userDto = UserDto.builder()
                .build();
        var users = new PageImpl<>(List.of(userDto));
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(Set.of(phrase), Set.of(phrase), pageNumber, pageSize))
                .thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchUsers(phrase, pageNumber, pageSize);
        //Then
        assertFalse(result.results().get("users")
                .isEmpty());
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(Set.of(phrase),
                Set.of(phrase), pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_posts_when_searching_posts() {
        //Given
        var postDto = DiscussionDto.builder()
                .build();
        var posts = new PageImpl<>(List.of(postDto));
        when(discussionDataReceiverFacade.getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize))
                .thenReturn(posts);
        //When
        var result = defaultSearchStrategy.searchPosts(phrase, pageNumber, pageSize);
        //Then
        assertFalse(result.results().get("posts")
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_banned_words_when_searching_banned_words() {
        //Given
        var bannedWord = BannedWordDto.builder()
                .build();
        var bannedWords = new PageImpl<>(List.of(bannedWord));
        when(bannedWordsCheckerFacade.getBannedWordByWordContaining(phrase, pageNumber, pageSize))
                .thenReturn(bannedWords);
        //When
        var result = defaultSearchStrategy.searchBannedWords(phrase, pageNumber, pageSize);
        //Then
        assertFalse(result.results().get("bannedWords")
                .isEmpty());
        verify(bannedWordsCheckerFacade, times(1)).getBannedWordByWordContaining(phrase, pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_reactions_when_searching_reactions() {
        //Given
        var reaction = ReactionDto.builder()
                .build();
        var reactions = new PageImpl<>(List.of(reaction));
        when(reactionDataReceiverFacade.getReactionsByNameContaining(phrase, pageNumber, pageSize))
                .thenReturn(reactions);
        //When
        var result = defaultSearchStrategy.searchReactions(phrase, pageNumber, pageSize);
        //Then
        assertFalse(result.results().get("reactions")
                .isEmpty());
        verify(reactionDataReceiverFacade, times(1)).getReactionsByNameContaining(phrase, pageNumber, pageSize);
    }
}
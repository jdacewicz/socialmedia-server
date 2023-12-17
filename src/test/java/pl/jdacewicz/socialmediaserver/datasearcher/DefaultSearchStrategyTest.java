package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DefaultSearchStrategyTest {

    final String dataType = DefaultSearchStrategy.postSearchType;
    final String phrase = "test";
    final int pageNumber = 0;
    final int pageSize = 1;

    DefaultSearchStrategy defaultSearchStrategy;

    UserDataReceiverFacade userDataReceiverFacade;
    DiscussionDataReceiverFacade discussionDataReceiverFacade;


    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        discussionDataReceiverFacade = Mockito.mock(DiscussionDataReceiverFacade.class);
        defaultSearchStrategy = new DefaultSearchStrategy(userDataReceiverFacade, discussionDataReceiverFacade);
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
        assertFalse(result.posts()
                .isEmpty());
        assertFalse(result.users()
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
        assertFalse(result.users()
                .isEmpty());
        assertTrue(result.posts()
                .isEmpty());
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(Set.of(phrase),
                Set.of(phrase), pageNumber, pageSize);
        verify(discussionDataReceiverFacade, never()).getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize);
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
        assertFalse(result.posts()
                .isEmpty());
        assertTrue(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getDiscussionsByContentContaining(phrase, dataType, pageNumber, pageSize);
        verify(userDataReceiverFacade, never()).getUsersByFirstnamesAndLastnames(Set.of(phrase), Set.of(phrase), pageNumber, pageSize);
    }
}
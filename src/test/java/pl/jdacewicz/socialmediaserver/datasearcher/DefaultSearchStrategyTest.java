package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
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

    final SearchRequest searchRequest = new SearchRequest("test");
    final String dataType = "BASIC";
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
    void should_return_search_result_with_users_comments_and_posts_when_searching_all() {
        //Given
        var postDto = DiscussionDto.builder()
                .build();
        var commentDto = CommentDto.builder()
                .build();
        var userDto = UserDto.builder()
                .build();
        var posts = new PageImpl<>(List.of(postDto));
        var comments = new PageImpl<>(List.of(commentDto));
        var users = new PageImpl<>(List.of(userDto));
        when(discussionDataReceiverFacade.getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize))
                .thenReturn(posts);
        when(discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize))
                .thenReturn(comments);
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()), Set.of(searchRequest.phrase()), pageNumber, pageSize))
                .thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchAll(searchRequest, pageNumber, pageSize);
        //Then
        assertFalse(result.posts()
                .isEmpty());
        assertFalse(result.comments()
                .isEmpty());
        assertFalse(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(discussionDataReceiverFacade, times(1)).getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()),
                Set.of(searchRequest.phrase()), pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_users_when_searching_users() {
        //Given
        var userDto = UserDto.builder()
                .build();
        var users = new PageImpl<>(List.of(userDto));
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()), Set.of(searchRequest.phrase()), pageNumber, pageSize))
                .thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchUsers(searchRequest, pageNumber, pageSize);
        //Then
        assertFalse(result.users()
                .isEmpty());
        assertTrue(result.posts()
                .isEmpty());
        assertTrue(result.comments()
                .isEmpty());
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()),
                Set.of(searchRequest.phrase()), pageNumber, pageSize);
        verify(discussionDataReceiverFacade, never()).getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(discussionDataReceiverFacade, never()).getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_posts_when_searching_posts() {
        //Given
        var postDto = DiscussionDto.builder()
                .build();
        var posts = new PageImpl<>(List.of(postDto));
        when(discussionDataReceiverFacade.getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize))
                .thenReturn(posts);
        //When
        var result = defaultSearchStrategy.searchPosts(searchRequest, pageNumber, pageSize);
        //Then
        assertFalse(result.posts()
                .isEmpty());
        assertTrue(result.comments()
                .isEmpty());
        assertTrue(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(discussionDataReceiverFacade, never()).getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(userDataReceiverFacade, never()).getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()), Set.of(searchRequest.phrase()), pageNumber, pageSize);
    }

    @Test
    void should_return_search_result_with_comments_when_searching_comments() {
        //Given
        var commentDto = CommentDto.builder()
                .build();
        var comments = new PageImpl<>(List.of(commentDto));
        when(discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize))
                .thenReturn(comments);
        //When
        var result = defaultSearchStrategy.searchComments(searchRequest, pageNumber, pageSize);
        //Then
        assertFalse(result.comments()
                .isEmpty());
        assertTrue(result.posts()
                .isEmpty());
        assertTrue(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getCommentsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(discussionDataReceiverFacade, never()).getDiscussionsByContentContaining(searchRequest.phrase(), dataType, pageNumber, pageSize);
        verify(userDataReceiverFacade, never()).getUsersByFirstnamesAndLastnames(Set.of(searchRequest.phrase()), Set.of(searchRequest.phrase()), pageNumber, pageSize);
    }
}
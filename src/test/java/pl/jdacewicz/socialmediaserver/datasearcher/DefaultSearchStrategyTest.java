package pl.jdacewicz.socialmediaserver.datasearcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.datasearcher.dto.SearchRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultSearchStrategyTest {

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
        var searchRequest = new SearchRequest("test");
        var postDto = PostDto.builder()
                .build();
        var commentDto = CommentDto.builder()
                .build();
        var userDto = UserDto.builder()
                .build();
        var posts = Set.of(postDto);
        var comments = Set.of(commentDto);
        var users = Set.of(userDto);
        when(discussionDataReceiverFacade.getPostsByContentContaining(searchRequest.typedInText())).thenReturn(posts);
        when(discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.typedInText())).thenReturn(comments);
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(any(), any())).thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchAll(searchRequest);
        //Then
        assertFalse(result.posts()
                .isEmpty());
        assertFalse(result.comments()
                .isEmpty());
        assertFalse(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getPostsByContentContaining(anyString());
        verify(discussionDataReceiverFacade, times(1)).getCommentsByContentContaining(anyString());
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(any(), any());
    }

    @Test
    void should_return_search_result_with_users_when_searching_users() {
        //Given
        var searchRequest = new SearchRequest("test");
        var userDto = UserDto.builder()
                .build();
        var users = Set.of(userDto);
        when(userDataReceiverFacade.getUsersByFirstnamesAndLastnames(any(), any())).thenReturn(users);
        //When
        var result = defaultSearchStrategy.searchUsers(searchRequest);
        //Then
        assertFalse(result.users()
                .isEmpty());
        assertTrue(result.posts()
                .isEmpty());
        assertTrue(result.comments()
                .isEmpty());
        verify(userDataReceiverFacade, times(1)).getUsersByFirstnamesAndLastnames(any(), any());
        verify(discussionDataReceiverFacade, never()).getPostsByContentContaining(anyString());
        verify(discussionDataReceiverFacade, never()).getCommentsByContentContaining(anyString());
    }

    @Test
    void should_return_search_result_with_posts_when_searching_posts() {
        //Given
        var searchRequest = new SearchRequest("test");
        var postDto = PostDto.builder()
                .build();
        var posts = Set.of(postDto);
        when(discussionDataReceiverFacade.getPostsByContentContaining(searchRequest.typedInText())).thenReturn(posts);
        //When
        var result = defaultSearchStrategy.searchPosts(searchRequest);
        //Then
        assertFalse(result.posts()
                .isEmpty());
        assertTrue(result.comments()
                .isEmpty());
        assertTrue(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getPostsByContentContaining(anyString());
        verify(discussionDataReceiverFacade, never()).getCommentsByContentContaining(anyString());
        verify(userDataReceiverFacade, never()).getUsersByFirstnamesAndLastnames(any(), any());
    }

    @Test
    void should_return_search_result_with_comments_when_searching_comments() {
        //Given
        var searchRequest = new SearchRequest("test");
        var commentDto = CommentDto.builder()
                .build();
        var comments = Set.of(commentDto);
        when(discussionDataReceiverFacade.getCommentsByContentContaining(searchRequest.typedInText())).thenReturn(comments);
        //When
        var result = defaultSearchStrategy.searchComments(searchRequest);
        //Then
        assertFalse(result.comments()
                .isEmpty());
        assertTrue(result.posts()
                .isEmpty());
        assertTrue(result.users()
                .isEmpty());
        verify(discussionDataReceiverFacade, times(1)).getCommentsByContentContaining(anyString());
        verify(discussionDataReceiverFacade, never()).getPostsByContentContaining(anyString());
        verify(userDataReceiverFacade, never()).getUsersByFirstnamesAndLastnames(any(), any());
    }
}
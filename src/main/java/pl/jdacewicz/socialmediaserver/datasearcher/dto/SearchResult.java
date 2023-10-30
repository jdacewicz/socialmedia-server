package pl.jdacewicz.socialmediaserver.datasearcher.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.HashSet;
import java.util.Set;

@Builder
public record SearchResult(Set<UserDto> users,
                           Set<PostDto> posts,
                           Set<CommentDto> comments) {

    @SuppressWarnings("unused")
    public static class SearchResultBuilder {
        private Set<UserDto> users = new HashSet<>();
        private Set<PostDto> posts = new HashSet<>();
        private Set<CommentDto> comments = new HashSet<>();
    }
}

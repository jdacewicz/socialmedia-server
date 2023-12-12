package pl.jdacewicz.socialmediaserver.datasearcher.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@Builder
public record SearchResult(Page<UserDto> users,
                           Page<PostDto> posts,
                           Page<CommentDto> comments) {

    @SuppressWarnings("unused")
    public static class SearchResultBuilder {
        private Page<UserDto> users = Page.empty();
        private Page<PostDto> posts = Page.empty();
        private Page<CommentDto> comments = Page.empty();
    }
}

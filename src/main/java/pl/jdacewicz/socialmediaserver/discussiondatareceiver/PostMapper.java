package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PostMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;
    private final CommentMapper commentMapper;
    private final FileMapperFacade fileMapperFacade;

    public List<PostDto> mapToDto(List<Post> randomPosts) {
        return randomPosts.stream()
                .map(this::mapToDto)
                .toList();
    }

    public Set<PostDto> mapToDto(Set<Post> foundPosts) {
        return foundPosts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    PostDto mapToDto(Post post) {
        var image = fileMapperFacade.mapToFile(new MapRequest(post.getImageName(), post.getFolderDirectory()));
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(post.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(post.getReactionUsers());
        var comments = commentMapper.mapToDto(post.getComments());
        return PostDto.builder()
                .postId(post.getPostId())
                .content(EmojiParser.parseToUnicode(post.getContent()))
                .creator(post.getCreator())
                .image(image)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .comments(comments)
                .build();
    }
}

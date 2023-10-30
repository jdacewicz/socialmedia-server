package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionImage;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PostMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;
    private final CommentMapper commentMapper;

    public Set<PostDto> mapToDto(Set<Post> foundPosts) {
        return foundPosts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    PostDto mapToDto(Post post) {
        var discussionImage = new DiscussionImage(post.getImageName(), post.getImageMainDirectory());
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(post.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(post.getReactionUsers());
        var comments = commentMapper.mapToDto(post.getComments());
        return PostDto.builder()
                .postId(post.getPostId())
                .content(EmojiParser.parseToUnicode(post.getContent()))
                .creator(post.getCreator())
                .image(discussionImage)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .comments(comments)
                .build();
    }
}

package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionImage;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CommentMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;

    Set<CommentDto> mapToDto(Set<Comment> comments) {
        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    CommentDto mapToDto(Comment comment) {
        var discussionImage = new DiscussionImage(comment.getImageName(), comment.getImageMainDirectory());
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(comment.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(comment.getReactionUsers());
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .creator(comment.getCreator())
                .image(discussionImage)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .build();
    }
}

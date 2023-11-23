package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CommentMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;
    private final FileMapperFacade fileMapperFacade;

    Set<CommentDto> mapToDto(Set<Comment> comments) {
        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    CommentDto mapToDto(Comment comment) {
        var image = fileMapperFacade.mapToFile(new MapRequest(comment.getImageName(), comment.getFolderDirectory()));
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(comment.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(comment.getReactionUsers());
        var parsedContent = EmojiParser.parseToUnicode(comment.getContent());
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .content(parsedContent)
                .creator(comment.getCreator())
                .image(image)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .build();
    }
}

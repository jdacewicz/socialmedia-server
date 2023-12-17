package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

import java.util.List;

@Component
@RequiredArgsConstructor
class DiscussionMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;
    private final FileMapperFacade fileMapperFacade;


    public List<DiscussionDto> mapToDto(List<? extends Post> foundPosts) {
        return foundPosts.stream()
                .map(this::mapToDto)
                .toList();
    }

    public DiscussionDto mapToDto(Discussion<?> discussion) {
        var image = fileMapperFacade.mapToFile(new MapRequest(discussion.getImageName(), discussion.getFolderDirectory()));
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(discussion.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(discussion.getReactionUsers());
        return DiscussionDto.builder()
                .postId(discussion.getDiscussionId())
                .content(EmojiParser.parseToUnicode(discussion.getContent()))
                .creator(discussion.getCreator())
                .image(image)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .build();
    }
}

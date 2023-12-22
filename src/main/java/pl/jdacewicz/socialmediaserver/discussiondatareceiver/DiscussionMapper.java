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
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DiscussionMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;
    private final FileMapperFacade fileMapperFacade;


    List<DiscussionDto> mapToDto(List<? extends Post> posts) {
        return posts.stream()
                .map(this::mapToDto)
                .toList();
    }

    Set<DiscussionDto> mapToDto(Set<? extends Discussion<?>> discussions) {
        return discussions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    DiscussionDto mapToDto(Discussion<?> discussion) {
        var image = fileMapperFacade.mapToFile(new MapRequest(discussion.getImageName(), discussion.getFolderDirectory()));
        var elapsedDateTime = elapsedDateTimeFormatterFacade.formatDateTime(discussion.getCreationDateTime());
        var reactionCounts = reactionCounterFacade.countReactions(discussion.getReactionUsers());
        return DiscussionDto.builder()
                .discussionId(discussion.getDiscussionId())
                .content(EmojiParser.parseToUnicode(discussion.getContent()))
                .creator(discussion.getCreator())
                .image(image)
                .elapsedDateTime(elapsedDateTime)
                .reactionCounts(reactionCounts)
                .build();
    }
}

package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.ElapsedDateTimeFormatterFacade;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

@Component
@RequiredArgsConstructor
class PostMapper {

    private final ReactionCounterFacade reactionCounterFacade;
    private final ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade;

    PostDto mapToDto(Post post) {
        return PostDto.builder()
                .postId(post.postId())
                .content(post.content())
                .creator(post.creator())
                .imageUrl(post.getPostImageDirectory())
                .elapsedDateTime(elapsedDateTimeFormatterFacade.formatDateTime(post.creationDateTime()))
                .reactionCounts(reactionCounterFacade.countReactions(post.reactionUsers()))
                .build();
    }
}

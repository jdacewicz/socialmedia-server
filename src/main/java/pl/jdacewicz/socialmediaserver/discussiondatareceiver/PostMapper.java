package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.reactioncounter.ReactionCounterFacade;

@Component
@RequiredArgsConstructor
class PostMapper {

    private final ReactionCounterFacade reactionCounterFacade;

    PostDto mapToDto(Post post) {
        return PostDto.builder()
                .postId(post.postId())
                .content(post.content())
                .creator(post.creator())
                .reactionCounts(reactionCounterFacade.countReactions(post.reactionUsers()))
                .build();
    }
}

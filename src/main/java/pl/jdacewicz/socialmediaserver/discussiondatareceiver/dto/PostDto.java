package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;

import java.util.Set;

@Builder
public record PostDto(String postId,
                      String content,
                      Set<ReactionCount> reactionCounts) {
}

package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

@Builder
public record PostDto(String postId,
                      String content,
                      UserDto creator,
                      String imageUrl,
                      Set<ReactionCount> reactionCounts) {
}

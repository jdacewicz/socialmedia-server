package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.dto.ElapsedDateTime;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

@Builder
public record PostDto(String postId,
                      String content,
                      UserDto creator,
                      DiscussionImage image,
                      ElapsedDateTime elapsedDateTime,
                      Set<ReactionCount> reactionCounts) {
}

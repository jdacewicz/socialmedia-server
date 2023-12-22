package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.dto.ElapsedDateTime;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;

@Builder
public record DiscussionDto(String discussionId,
                            String content,
                            UserDto creator,
                            File image,
                            ElapsedDateTime elapsedDateTime,
                            Set<ReactionCount> reactionCounts) {
}

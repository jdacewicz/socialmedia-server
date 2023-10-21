package pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@Builder
public record ReactionUser(ReactionDto reaction,
                           UserDto reactor) {
}

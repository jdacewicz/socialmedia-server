package pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;

@Builder
public record ReactionDto(String reactionId,
                          String name,
                          File image) {
}

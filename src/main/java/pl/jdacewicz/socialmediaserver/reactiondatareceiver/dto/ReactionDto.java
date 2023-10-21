package pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto;

import lombok.Builder;

@Builder
public record ReactionDto(String reactionId,
                          String imageUrl) {
}

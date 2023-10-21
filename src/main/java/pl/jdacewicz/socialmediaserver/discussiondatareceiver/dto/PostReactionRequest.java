package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;

@Builder
public record PostReactionRequest(String postId,
                                  String reactionId) {
}

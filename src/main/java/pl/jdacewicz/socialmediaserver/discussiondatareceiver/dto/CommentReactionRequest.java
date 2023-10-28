package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;

@Builder
public record CommentReactionRequest(String commentId,
                                     String reactionId) {
}

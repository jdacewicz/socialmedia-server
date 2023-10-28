package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

public record CommentReactionRequest(String commentId,
                                     String reactionId) {
}

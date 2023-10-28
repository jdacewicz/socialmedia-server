package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

public record CommentRequest(String postId,
                             String content) {
}

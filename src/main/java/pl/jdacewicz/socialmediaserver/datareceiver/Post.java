package pl.jdacewicz.socialmediaserver.datareceiver;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
record Post(String postId,
            String content,
            LocalDateTime creationDateTime) {
}

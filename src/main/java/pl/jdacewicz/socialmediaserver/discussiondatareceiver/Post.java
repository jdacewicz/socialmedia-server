package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
record Post(@Id
            String postId,

            String content,

            LocalDateTime creationDateTime) {
}

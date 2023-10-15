package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
record Reaction(@Id
                String reactionId,

                String name,

                String imageName) {
}

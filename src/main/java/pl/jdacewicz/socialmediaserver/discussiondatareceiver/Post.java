package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionUser;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Builder
record Post(@Id
            String postId,

            String content,

            LocalDateTime creationDateTime,

            List<ReactionUser> reactionUsers) {

    static class PostBuilder {
        private List<ReactionUser> reactionUsers = new LinkedList<>();
    }
}

package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Builder
record Post(@Id
            String postId,

            String content,

            UserDto creator,

            LocalDateTime creationDateTime,

            List<ReactionUser> reactionUsers) {

    static class PostBuilder {
        private List<ReactionUser> reactionUsers = new LinkedList<>();
    }
}

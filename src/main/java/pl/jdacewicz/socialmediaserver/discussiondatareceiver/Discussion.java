package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
abstract class Discussion<T> {

    private String content;

    private UserDto creator;

    private String imageName;

    @Builder.Default
    private LocalDateTime creationDateTime = LocalDateTime.now();

    @Builder.Default
    private List<ReactionUser> reactionUsers = new LinkedList<>();

    abstract T withReactionUser(ReactionUser reactionUser);

    abstract T withoutReactionUser(ReactionUser reactionUser);

    abstract String getImageDirectory();

    abstract String getFolderDirectory();

    boolean isReactionUserStored(ReactionUser reactionUser) {
        return reactionUsers.contains(reactionUser);
    }
}

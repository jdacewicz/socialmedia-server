package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 255)
    private String content;

    @NotNull
    private UserDto creator;

    private String imageName;

    @NotBlank
    private String imageMainDirectory;

    @Builder.Default
    private LocalDateTime creationDateTime = LocalDateTime.now();

    @Builder.Default
    private List<ReactionUser> reactionUsers = new LinkedList<>();

    abstract T withReactionUser(ReactionUser reactionUser);

    abstract T withoutReactionUser(ReactionUser reactionUser);

    String getImageDirectory() {
        return String.format("%s/%s", getFolderDirectory(), imageName);
    }

    abstract String getFolderDirectory();

    boolean isReactionUserStored(ReactionUser reactionUser) {
        return reactionUsers.contains(reactionUser);
    }
}

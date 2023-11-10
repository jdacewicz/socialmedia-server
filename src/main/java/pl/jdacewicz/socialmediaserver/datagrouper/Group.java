package pl.jdacewicz.socialmediaserver.datagrouper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupParticipator;

import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
abstract class Group {

    @Id
    private String groupId;

    @NotBlank
    @Size(max = 32)
    private String name;

    private String imageName;

    @NotNull
    private GroupParticipator owner;

    @NotNull
    private Set<GroupParticipator> participants;

    @NotNull
    private Set<GroupParticipator> admins;

    @Builder.Default
    private Set<GroupParticipator> bannedUsers = new HashSet<>();

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean inviteOnly = true;

    @Builder.Default
    private boolean invitationRevoked = true;

    final static String MAIN_DIRECTORY = "data/groups";

    String getImageDirectory() {
        return String.format("%s/%s", getFolderDirectory(), imageName);
    }

    abstract String getFolderDirectory();
}

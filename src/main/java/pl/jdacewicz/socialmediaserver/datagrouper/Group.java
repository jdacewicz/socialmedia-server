package pl.jdacewicz.socialmediaserver.datagrouper;

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

    private String name;

    private String imageName;

    private GroupParticipator owner;

    private Set<GroupParticipator> participants;

    private Set<GroupParticipator> admins;

    @Builder.Default
    private Set<GroupParticipator> bannedUsers = new HashSet<>();

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean inviteOnly = true;

    @Builder.Default
    private boolean invitationRevoked = true;

    String getImageDirectory() {
        return String.format("%s/%s", getFolderDirectory(), imageName);
    }

    abstract String getFolderDirectory();
}

package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
abstract class Group {

    private String name;

    private String imageName;

    private UserDto owner;

    @Builder.Default
    private Set<UserDto> participants = new HashSet<>();

    @Builder.Default
    private Set<UserDto> admins = new HashSet<>();

    @Builder.Default
    private Set<UserDto> bannedUsers = new HashSet<>();

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

package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserProfilePicture;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class UserMapper {

    Set<UserDto> mapToDto(Set<User> users) {
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    UserDto mapToDto(User user) {
        var profilePicture = new UserProfilePicture(user.profilePictureName(), user.getFolderDirectory());
        return UserDto.builder()
                .userId(user.userId())
                .firstname(user.firstname())
                .lastname(user.lastname())
                .profilePicture(profilePicture)
                .build();
    }
}

package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserMapper {

    private final FileMapperFacade fileMapperFacade;

    Set<UserDto> mapToDto(Set<User> users) {
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    UserDto mapToDto(User user) {
        var profilePicture = fileMapperFacade.mapToFile(new MapRequest(user.profilePictureName(), user.getFolderDirectory()));
        return UserDto.builder()
                .userId(user.userId())
                .fullName(user.getFullName())
                .profilePicture(profilePicture)
                .build();
    }

    LoggedUserDto mapToLoggedDto(User user) {
        var profilePicture = fileMapperFacade.mapToFile(new MapRequest(user.profilePictureName(), user.getFolderDirectory()));
        return LoggedUserDto.builder()
                .userId(user.userId())
                .fullName(user.getFullName())
                .dataDirectory(user.getFolderDirectory())
                .profilePicture(profilePicture)
                .build();
    }
}

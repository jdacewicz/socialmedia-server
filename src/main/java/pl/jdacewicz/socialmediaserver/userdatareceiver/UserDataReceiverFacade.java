package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@RequiredArgsConstructor
public class UserDataReceiverFacade {

    private final UserDataReceiverService userDataReceiverService;

    public UserDto getUserByEmail(String email) {
        var user = userDataReceiverService.getUserByEmail(email);
        return mapToDto(user);
    }

    public UserDto registerUser(RegisterRequest registerRequest) {
        var createdUser = userDataReceiverService.registerUser(registerRequest);
        return mapToDto(createdUser);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.userId())
                .firstname(user.firstname())
                .firstname(user.lastname())
                .profilePictureUrl(user.profilePictureName())
                .build();
    }
}

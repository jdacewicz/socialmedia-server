package pl.jdacewicz.socialmediaserver.userdatareceiver.dto;

import lombok.Builder;

@Builder
public record UserDto(String userId,
                      String fullName,
                      UserProfilePicture profilePicture) {
}

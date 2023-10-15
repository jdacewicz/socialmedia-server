package pl.jdacewicz.socialmediaserver.userauthenticator.dto;

public record UserDto(String userId,
                      String firstname,
                      String lastname,
                      String profilePictureUrl) {
}

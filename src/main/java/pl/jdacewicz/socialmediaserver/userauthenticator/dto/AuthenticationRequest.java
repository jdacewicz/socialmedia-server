package pl.jdacewicz.socialmediaserver.userauthenticator.dto;

public record AuthenticationRequest(String email,
                                    String password) {
}

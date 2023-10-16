package pl.jdacewicz.socialmediaserver.userauthenticator.dto;

public record RegisterRequest(String email,
                              String password,
                              String firstname,
                              String lastname) {
}

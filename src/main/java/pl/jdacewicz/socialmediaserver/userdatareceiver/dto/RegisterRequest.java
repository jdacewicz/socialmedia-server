package pl.jdacewicz.socialmediaserver.userdatareceiver.dto;

public record RegisterRequest(String email,
                              String password,
                              String firstname,
                              String lastname) {
}

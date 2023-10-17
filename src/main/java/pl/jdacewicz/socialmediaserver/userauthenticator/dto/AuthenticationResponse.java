package pl.jdacewicz.socialmediaserver.userauthenticator.dto;

import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;

public record AuthenticationResponse(TokenDto accessToken) {
}

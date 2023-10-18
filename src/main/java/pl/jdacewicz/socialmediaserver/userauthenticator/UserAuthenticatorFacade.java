package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationResponse;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

@RequiredArgsConstructor
public class UserAuthenticatorFacade {

    private final UserAuthenticatorService userAuthenticatorService;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        var tokenDto = userAuthenticatorService.authenticateUser(authenticationRequest);
        return new AuthenticationResponse(tokenDto);
    }

    public AuthenticationResponse registerUser(RegisterRequest registerRequest) {
        var tokenDto = userAuthenticatorService.registerUser(registerRequest);
        return new AuthenticationResponse(tokenDto);
    }
}

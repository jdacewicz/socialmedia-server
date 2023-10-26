package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationResponse;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import java.io.IOException;

@RequiredArgsConstructor
public class UserAuthenticatorFacade {

    private final UserAuthenticatorService userAuthenticatorService;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        var tokenDto = userAuthenticatorService.authenticateUser(authenticationRequest);
        return new AuthenticationResponse(tokenDto);
    }

    public AuthenticationResponse registerUser(MultipartFile profileImage, RegisterRequest registerRequest) throws IOException {
        var tokenDto = userAuthenticatorService.registerUser(profileImage, registerRequest);
        return new AuthenticationResponse(tokenDto);
    }
}

package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationResponse;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

@RequiredArgsConstructor
public class UserAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final TokenGeneratorFacade tokenGeneratorFacade;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        TokenDto tokenDto;
        var userDto = userDataReceiverFacade.getUserByEmail(authenticationRequest.email());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()));
        tokenGeneratorFacade.revokeAllUserTokens(userDto.userId());
        tokenDto = tokenGeneratorFacade.createToken(authenticationRequest.email());
        return new AuthenticationResponse(tokenDto);
    }

    public AuthenticationResponse registerUser(RegisterRequest registerRequest) {
        TokenDto tokenDto;
        userDataReceiverFacade.createUser(registerRequest);
        tokenDto = tokenGeneratorFacade.createToken(registerRequest.email());
        return new AuthenticationResponse(tokenDto);
    }
}

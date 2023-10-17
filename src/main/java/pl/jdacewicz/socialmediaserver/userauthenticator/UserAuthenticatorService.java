package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@Service
@RequiredArgsConstructor
class UserAuthenticatorService {

    private final AuthenticationManager authenticationManager;
    private final UserDataReceiverFacade dataReceiverFacade;
    private final TokenGeneratorFacade tokenGeneratorFacade;

    UserDto authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()));
        var user = dataReceiverFacade.getUserByEmail(authenticationRequest.email());
        tokenGeneratorFacade.revokeAllUserTokens(user.userId());
        return user;
    }
}

package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

@Service
@RequiredArgsConstructor
class UserAuthenticatorService {

    private final AuthenticationManager authenticationManager;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final TokenGeneratorFacade tokenGeneratorFacade;

    TokenDto authenticateUser(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()));
        revokeAllUserTokensByUserEmail(authenticationRequest.email());
        return tokenGeneratorFacade.createToken(authenticationRequest.email());
    }

    TokenDto registerUser(RegisterRequest registerRequest) {
        userDataReceiverFacade.createUser(registerRequest);
        return tokenGeneratorFacade.createToken(registerRequest.email());
    }

    private void revokeAllUserTokensByUserEmail(String email) {
        var userDto = userDataReceiverFacade.getUserByEmail(email);
        tokenGeneratorFacade.revokeAllUserTokensByUserId(userDto.userId());
    }
}

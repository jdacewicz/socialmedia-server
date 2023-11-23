package pl.jdacewicz.socialmediaserver.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
class UserAuthenticatorService {

    private final AuthenticationManager authenticationManager;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final TokenGeneratorFacade tokenGeneratorFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    TokenDto authenticateUser(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()));
        revokeAllUserTokensByUserEmail(authenticationRequest.email());
        return tokenGeneratorFacade.createToken(authenticationRequest.email());
    }

    TokenDto registerUser(MultipartFile profileImage, RegisterRequest registerRequest) throws IOException {
        bannedWordsCheckerFacade.checkForBannedWords(registerRequest.getFirstAndLastName());
        userDataReceiverFacade.createUser(profileImage, registerRequest);
        return tokenGeneratorFacade.createToken(registerRequest.email());
    }

    private void revokeAllUserTokensByUserEmail(String email) {
        var userDto = userDataReceiverFacade.getUserByEmail(email);
        tokenGeneratorFacade.revokeAllUserTokensByUserId(userDto.getUserId());
    }
}

package pl.jdacewicz.socialmediaserver.infrastructure.controller.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.userauthenticator.UserAuthenticatorFacade;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationResponse;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class UserAuthenticatorRestController {

    private final UserAuthenticatorFacade userAuthenticatorFacade;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestPart MultipartFile profileImage,
                                           @RequestPart RegisterRequest request) throws IOException {
        return userAuthenticatorFacade.registerUser(profileImage, request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return userAuthenticatorFacade.authenticateUser(request);
    }
}

package pl.jdacewicz.socialmediaserver.infrastructure.controller.userauthenticator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdacewicz.socialmediaserver.userauthenticator.UserAuthenticatorFacade;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationResponse;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class UserAuthenticatorRestController {

    private final UserAuthenticatorFacade userAuthenticatorFacade;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return userAuthenticatorFacade.registerUser(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return userAuthenticatorFacade.authenticateUser(request);
    }
}

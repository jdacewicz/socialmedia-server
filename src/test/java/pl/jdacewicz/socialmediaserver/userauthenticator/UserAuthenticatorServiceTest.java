package pl.jdacewicz.socialmediaserver.userauthenticator;

import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;
import pl.jdacewicz.socialmediaserver.userauthenticator.dto.AuthenticationRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserAuthenticatorServiceTest {

    UserAuthenticatorService userAuthenticatorService;

    AuthenticationManager authenticationManager;
    UserDataReceiverFacade userDataReceiverFacade;
    TokenGeneratorFacade tokenGeneratorFacade;

    @BeforeEach
    void setUp() {
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        tokenGeneratorFacade = Mockito.mock(TokenGeneratorFacade.class);
        userAuthenticatorService = new UserAuthenticatorService(authenticationManager, userDataReceiverFacade,
                tokenGeneratorFacade);
    }

    @Test
    public void should_authenticate_successfully_when_valid_credentials_provided() {
        //Given
        var authenticationRequest = new AuthenticationRequest("test@example.com", "password");
        var userDto = new UserDto("id", "firstname", "lastname", "profilePictureUrl");
        var tokenDto = new TokenDto("code", true);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()));
        when(userDataReceiverFacade.getUserByEmail(authenticationRequest.email())).thenReturn(userDto);
        when(tokenGeneratorFacade.createToken(authenticationRequest.email())).thenReturn(tokenDto);
        //When
        var result = userAuthenticatorService.authenticateUser(authenticationRequest);
        //Then
        assertEquals(tokenDto, result);
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userDataReceiverFacade, times(1)).getUserByEmail(authenticationRequest.email());
        verify(tokenGeneratorFacade, times(1)).revokeAllUserTokensByUserId(userDto.userId());
        verify(tokenGeneratorFacade, times(1)).createToken(authenticationRequest.email());
    }

    @Test
    public void should_fail_to_authenticate_when_invalid_credentials_provided() {
        //Given
        var authenticationRequest = new AuthenticationRequest("invalid email", "invalid password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);
        //When
        //Then
        assertThrows(BadCredentialsException.class, () -> userAuthenticatorService.authenticateUser(authenticationRequest));
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userDataReceiverFacade, never()).getUserByEmail(anyString());
        verify(tokenGeneratorFacade, never()).revokeAllUserTokensByUserId(anyString());
        verify(tokenGeneratorFacade, never()).createToken(anyString());
    }

    @Test
    public void should_register_successfully_when_valid_data_provided() throws IOException {
        //Given
        var profileImage = new MockMultipartFile("name", "content".getBytes());
        var registerRequest = new RegisterRequest("test@example.com", "password", "firstname", "lastname");
        var tokenDto = new TokenDto("code", true);
        when(tokenGeneratorFacade.createToken(registerRequest.email())).thenReturn(tokenDto);
        //When
        var result = userAuthenticatorService.registerUser(profileImage, registerRequest);
        //Then
        assertEquals(tokenDto, result);
        verify(userDataReceiverFacade, times(1)).createUser(profileImage, registerRequest);
        verify(tokenGeneratorFacade, times(1)).createToken(registerRequest.email());
    }

    @Test
    public void should_fail_to_register_when_not_unique_email_provided() throws IOException {
        // Arrange
        var profileImage = new MockMultipartFile("name", "content".getBytes());
        var registerRequest = new RegisterRequest("invalid email", "password", "firstname", "lastname");
        when(userDataReceiverFacade.createUser(profileImage, registerRequest)).thenThrow(DuplicateKeyException.class);
        //When
        //Then
        assertThrows(DuplicateKeyException.class,
                () -> userAuthenticatorService.registerUser(profileImage, registerRequest));
        verify(userDataReceiverFacade, times(1)).createUser(profileImage, registerRequest);
        verify(tokenGeneratorFacade, never()).createToken(anyString());
    }
}
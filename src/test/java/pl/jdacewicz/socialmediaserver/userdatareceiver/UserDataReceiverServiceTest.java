package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDataReceiverServiceTest {

    UserDataReceiverService userDataReceiverService;

    UserDataReceiverRepositoryTest userDataReceiverRepository;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        userDataReceiverRepository = new UserDataReceiverRepositoryTest();
        userDataReceiverService = new UserDataReceiverService(userDataReceiverRepository, passwordEncoder);
    }

    @Test
    public void should_return_user_when_getting_existing_user() {
        //Given
        var email = "test@example.com";
        var user = User.builder()
                .email(email)
                .build();
        userDataReceiverRepository.save(user);
        //When
       var result = userDataReceiverService.getUserByEmail(email);
        //Then
        assertEquals(email, result.email());
    }

    @Test
    public void should_throw_unsupported_operation_exception_when_getting_not_existing_user() {
        //Given
        var email = "test@example.com";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> userDataReceiverService.loadUserByUsername(email));
    }

    @Test
    public void should_return_user_when_user_is_logged_and_is_getting_logged_user() {
        //Given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        User user = User.builder()
                .userId("id")
                .build();
        when(auth.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(auth);
        //When
        var result = userDataReceiverService.getLoggedInUser();
        //Then
        assertEquals(user, result);
    }

    @Test
    public void should_throw_unsupported_operation_exception_when_user_is_not_logged_and_is_getting_logged_user() {
        //Given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> userDataReceiverService.getLoggedInUser());
    }

    @Test
    public void should_return_user_when_creating_user() {
        //Given
        var registerRequest = new RegisterRequest("test@example.com", "password", "firstname", "lastname");
        var user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .build();
        userDataReceiverRepository.save(user);
        //When
        var result = userDataReceiverService.createUser(registerRequest);
        //Then
        assertEquals(registerRequest.email(), result.email());
        assertEquals(registerRequest.firstname(), result.firstname());
        assertEquals(registerRequest.lastname(), result.lastname());
        assertEquals(user.password(), result.password());
    }

}
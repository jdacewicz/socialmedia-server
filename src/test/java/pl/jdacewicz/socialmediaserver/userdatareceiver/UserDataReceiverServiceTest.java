package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserDataReceiverServiceTest {

    UserDataReceiverService userDataReceiverService;

    UserDataReceiverRepository userDataReceiverRepository;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userDataReceiverRepository = Mockito.mock(UserDataReceiverRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDataReceiverService = new UserDataReceiverService(userDataReceiverRepository, passwordEncoder);
    }

    @Test
    public void should_return_user_when_user_exist() {
        //Given
        String email = "test@example.com";
        User user = User.builder()
                .email(email)
                .build();
        when(userDataReceiverRepository.findByEmail(email)).thenReturn(Optional.of(user));
        //When
        User result = userDataReceiverService.getUserByEmail(email);
        //Then
        assertEquals(email, result.email());
    }

    @Test
    public void should_throw_unsupported_operation_exception_when_user_doesnt_exist() {
        //Given
        String email = "test@example.com";
        when(userDataReceiverRepository.findByEmail(email)).thenReturn(Optional.empty());
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> userDataReceiverService.loadUserByUsername(email));
    }

    @Test
    public void should_return_user_when_user_created() {
        //Given
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password", "firstname", "lastname");
        User user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .build();
        when(userDataReceiverRepository.save(user)).thenReturn(user);
        //When
        User result = userDataReceiverService.createUser(registerRequest);
        //Then
        assertEquals(registerRequest.email(), result.email());
        assertEquals(registerRequest.firstname(), result.firstname());
        assertEquals(registerRequest.lastname(), result.lastname());
        assertEquals(user.password(), result.password());
    }

}
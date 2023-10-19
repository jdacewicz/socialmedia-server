package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDataReceiverServiceTest {

    UserDataReceiverService userDataReceiverService;

    UserDataReceiverRepositoryTest userDataReceiverRepository;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDataReceiverRepository = new UserDataReceiverRepositoryTest();
        userDataReceiverService = new UserDataReceiverService(userDataReceiverRepository, passwordEncoder);
    }

    @Test
    public void should_return_user_when_user_exist() {
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
    public void should_throw_unsupported_operation_exception_when_user_doesnt_exist() {
        //Given
        var email = "test@example.com";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> userDataReceiverService.loadUserByUsername(email));
    }

    @Test
    public void should_return_user_when_user_created() {
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
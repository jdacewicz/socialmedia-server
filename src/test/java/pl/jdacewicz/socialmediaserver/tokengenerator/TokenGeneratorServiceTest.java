package pl.jdacewicz.socialmediaserver.tokengenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TokenGeneratorServiceTest {

    TokenGeneratorService tokenGeneratorService;

    TokenGeneratorRepositoryTest tokenGeneratorRepository;
    TokenGenerator tokenGenerator;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        tokenGeneratorRepository = new TokenGeneratorRepositoryTest();
        tokenGenerator = Mockito.mock(TokenGenerator.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        tokenGeneratorService = new TokenGeneratorService(tokenGeneratorRepository, tokenGenerator, userDataReceiverFacade);
    }

    @Test
    void should_return_token_when_getting_existing_token_by_code() {
        //Given
        var code = "code";
        var token = Token.builder()
                .code(code)
                .build();
        tokenGeneratorRepository.save(token);
        //When
        var result = tokenGeneratorService.getTokenByCode(code);
        //Then
        assertEquals(code, result.code());
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_not_existing_token_by_code() {
        //Given
        var code = "code";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> tokenGeneratorService.getTokenByCode(code));
    }

    @Test
    void should_return_created_token_when_creating_token() {
        //Given
        var username = "username";
        var userDto = UserDto.builder()
                .userId("id")
                .build();
        var tokenCode = "token";
        var token = Token.builder()
                .userId(userDto.userId())
                .code(tokenCode)
                .expired(false)
                .revoked(false)
                .build();
        when(userDataReceiverFacade.getUserByEmail(username)).thenReturn(userDto);
        when(tokenGenerator.generateToken(username)).thenReturn(tokenCode);
        tokenGeneratorRepository.save(token);
        //When
        var result = tokenGeneratorService.createToken(username);
        //Then
        assertEquals(userDto.userId(), result.userId());
        assertEquals(tokenCode, result.code());
        assertFalse(result.expired());
        assertFalse(result.revoked());
    }

    @Test
    void should_mark_all_found_tokens_as_revoked_when_revoking_all_user_tokens() {
        //Given
        String userId = "id";
        Token token = Token.builder()
                .userId(userId)
                .code("code")
                .revoked(false)
                .build();
        tokenGeneratorRepository.save(token);
        //When
        tokenGeneratorService.revokeAllUserTokens(userId);
        var result = tokenGeneratorRepository.findByUserId(userId)
                .stream()
                .allMatch(Token::revoked);
        //Then
        assertTrue(result);
    }
}
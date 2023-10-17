package pl.jdacewicz.socialmediaserver.tokengenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class TokenGeneratorService {

    private final TokenGeneratorRepository tokenGeneratorRepository;
    private final TokenGenerator tokenGenerator;
    private final UserDataReceiverFacade userDataReceiverFacade;

    Token getTokenByCode(String code) {
        return tokenGeneratorRepository.findByCode(code)
                .orElseThrow(UnsupportedOperationException::new);
    }

    void revokeAllUserTokens(String userId) {
        var userTokens = tokenGeneratorRepository.findByUserId(userId);
        var revokedTokens = revokeTokens(userTokens);
        tokenGeneratorRepository.saveAll(revokedTokens);
    }

    Token createToken(String username) {
        var preparedToken = prepareToken(username);
        return tokenGeneratorRepository.save(preparedToken);
    }

    private List<Token> revokeTokens(List<Token> userTokens) {
        return userTokens.stream()
                .map(Token::toggleRevoked)
                .toList();
    }

    private Token prepareToken(String username) {
        var userDetails = userDataReceiverFacade.getUserByEmail(username);
        var generatedTokenCode = tokenGenerator.generateToken(username);
        return Token.builder()
                .userId(userDetails.userId())
                .code(generatedTokenCode)
                .expired(false)
                .revoked(false)
                .build();
    }
}

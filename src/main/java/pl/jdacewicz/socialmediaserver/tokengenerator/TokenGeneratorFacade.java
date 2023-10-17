package pl.jdacewicz.socialmediaserver.tokengenerator;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.tokengenerator.dto.TokenDto;

@RequiredArgsConstructor
public class TokenGeneratorFacade {

    private final TokenGeneratorService tokenGeneratorService;

    public TokenDto getTokenByCode(String code) {
        var token = tokenGeneratorService.getTokenByCode(code);
        return mapToDto(token);
    }

    public TokenDto createToken(String username) {
        var createdToken = tokenGeneratorService.createToken(username);
        return mapToDto(createdToken);
    }

    public void revokeAllUserTokens(String userId) {
        tokenGeneratorService.revokeAllUserTokens(userId);
    }

    private TokenDto mapToDto(Token createdToken) {
        return TokenDto.builder()
                .code(createdToken.code())
                .active(createdToken.isTokenActive())
                .build();
    }
}

package pl.jdacewicz.socialmediaserver.tokengenerator;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
record Token(@Id
             String tokenId,
             String userId,
             String code,
             boolean revoked,
             boolean expired) {

    Token toggleRevoked() {
        return Token.builder()
                .tokenId(this.tokenId)
                .userId(this.userId)
                .code(this.code)
                .expired(this.expired)
                .revoked(true)
                .build();
    }

    public boolean isTokenActive() {
        return (!revoked && !expired);
    }
}

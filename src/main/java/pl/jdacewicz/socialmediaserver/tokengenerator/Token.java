package pl.jdacewicz.socialmediaserver.tokengenerator;

import org.springframework.data.annotation.Id;

record Token(@Id
             String tokenId,
             String code,
             boolean revoked,
             boolean expired) {

    public boolean isTokenActive() {
        return (!revoked && !expired);
    }
}

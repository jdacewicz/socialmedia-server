package pl.jdacewicz.socialmediaserver.reactionuser.dto;

import lombok.Builder;

@Builder
public record ReactionUser(String reactionId,
                           String userId) {
}

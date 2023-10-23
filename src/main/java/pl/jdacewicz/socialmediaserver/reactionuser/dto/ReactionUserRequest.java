package pl.jdacewicz.socialmediaserver.reactionuser.dto;

import lombok.Builder;

@Builder
public record ReactionUserRequest(String reactionId) {
}

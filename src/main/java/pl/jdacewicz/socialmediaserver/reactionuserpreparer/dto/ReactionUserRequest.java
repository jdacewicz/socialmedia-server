package pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto;

import lombok.Builder;

@Builder
public record ReactionUserRequest(String reactionId) {
}

package pl.jdacewicz.socialmediaserver.reactionuser.dto;

import jakarta.validation.constraints.NotBlank;

public record ReactionUserRequest(@NotBlank
                                  String reactionId) {
}

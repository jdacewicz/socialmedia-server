package pl.jdacewicz.socialmediaserver.reactionuser;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;

@RequiredArgsConstructor
public class ReactionUserFacade {

    private final ReactionUserService reactionUserService;

    public ReactionUser createReactionUser(ReactionUserRequest reactionUserRequest) {
        return reactionUserService.createReactionUser(reactionUserRequest);
    }
}

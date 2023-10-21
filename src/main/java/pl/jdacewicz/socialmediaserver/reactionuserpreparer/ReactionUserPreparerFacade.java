package pl.jdacewicz.socialmediaserver.reactionuserpreparer;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUserRequest;

@RequiredArgsConstructor
public class ReactionUserPreparerFacade {
    
    private final ReactionUserPreparerService reactionUserPreparerService;

    public ReactionUser prepareReactionUser(ReactionUserRequest reactionUserRequest) {
        return reactionUserPreparerService.prepareReactionUser(reactionUserRequest);
    }
}

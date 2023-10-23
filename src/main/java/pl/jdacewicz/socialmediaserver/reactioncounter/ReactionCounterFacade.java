package pl.jdacewicz.socialmediaserver.reactioncounter;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ReactionCounterFacade {

    private final ReactionCounterService reactionCounterService;

    public Set<ReactionCount> countReactions(List<ReactionUser> reactionUsers) {
        return reactionCounterService.countReactions(reactionUsers);
    }
}

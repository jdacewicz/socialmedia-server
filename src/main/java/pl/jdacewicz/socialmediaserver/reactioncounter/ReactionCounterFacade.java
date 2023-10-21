package pl.jdacewicz.socialmediaserver.reactioncounter;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReactionCounterFacade {

    private final ReactionCounter reactionCounter;

    public Set<ReactionCount> countReactions(List<ReactionUser> reactionUsers) {
        var reactionsCounts = reactionCounter.countAllReactions(reactionUsers);
        return reactionsCounts.entrySet()
                .stream()
                .map(entry -> new ReactionCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }
}

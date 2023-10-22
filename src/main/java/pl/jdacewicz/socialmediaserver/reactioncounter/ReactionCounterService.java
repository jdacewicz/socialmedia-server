package pl.jdacewicz.socialmediaserver.reactioncounter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionCount;
import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ReactionCounterService {

    private final ReactionCounter reactionCounter;
    private final ReactionDataReceiverFacade reactionDataReceiverFacade;

    public Set<ReactionCount> countReactions(List<ReactionUser> reactionUsers) {
        var activeReactions = reactionDataReceiverFacade.getAllReactions();
        var reactionCounts = reactionCounter.countReactions(activeReactions, reactionUsers);
        return reactionCounts.entrySet()
                .stream()
                .map(entry -> new ReactionCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }
}

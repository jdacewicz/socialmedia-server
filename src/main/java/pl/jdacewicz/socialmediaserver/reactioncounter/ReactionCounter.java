package pl.jdacewicz.socialmediaserver.reactioncounter;

import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ReactionCounter {

    Map<ReactionDto, Long> countAllReactions(List<ReactionUser> reactionUsers) {
        return reactionUsers.stream()
                .collect(Collectors.groupingBy(ReactionUser::reaction, Collectors.counting()));
    }
}

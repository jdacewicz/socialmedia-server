package pl.jdacewicz.socialmediaserver.reactioncounter;

import pl.jdacewicz.socialmediaserver.reactioncounter.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class ReactionCounter {

    Map<ReactionDto, Long> countReactions(List<ReactionDto> activeReactions, List<ReactionUser> reactionUsers) {
        var activeReactionsMap = toMap(activeReactions);
        return reactionUsers.stream()
                .filter(reactionUser -> activeReactionsMap.containsKey(reactionUser.reactionId()))
                .collect(Collectors.groupingBy(reactionUser -> activeReactionsMap.get(reactionUser.reactionId()),
                        Collectors.counting()));
    }

    private Map<String, ReactionDto> toMap(List<ReactionDto> reactionsDto) {
        return reactionsDto.stream()
                .collect(Collectors.toMap(ReactionDto::reactionId, Function.identity()));
    }
}

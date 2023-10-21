package pl.jdacewicz.socialmediaserver.reactioncounter.dto;

import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;

public record ReactionCount(ReactionDto reaction,
                            long count) {
}

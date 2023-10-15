package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

@RequiredArgsConstructor
public class ReactionDataReceiverFacade {

    private final ReactionDataReceiverService reactionDataReceiverService;

    public ReactionDto createReaction(ReactionRequest reactionRequest) {
        var createdReaction = reactionDataReceiverService.createReaction(reactionRequest);
        return new ReactionDto(createdReaction.reactionId(), createdReaction.imageName());
    }
}

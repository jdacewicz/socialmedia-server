package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

import java.util.List;

@RequiredArgsConstructor
public class ReactionDataReceiverFacade {

    private final ReactionDataReceiverService reactionDataReceiverService;

    public ReactionDto createReaction(ReactionRequest reactionRequest) {
        var createdReaction = reactionDataReceiverService.createReaction(reactionRequest);
        return mapToDto(createdReaction);
    }

    public ReactionDto getReactionById(String reactionId) {
        var foundReaction = reactionDataReceiverService.getReactionById(reactionId);
        return mapToDto(foundReaction);
    }

    public List<ReactionDto> getAllReactions() {
        return reactionDataReceiverService.getAllReactions()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private ReactionDto mapToDto(Reaction reaction) {
        return new ReactionDto(reaction.reactionId(), reaction.imageName());
    }
}

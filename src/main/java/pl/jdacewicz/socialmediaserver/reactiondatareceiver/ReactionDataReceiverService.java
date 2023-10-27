package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionUpdateRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
class ReactionDataReceiverService {

    private final ReactionDataReceiverRepository reactionDataReceiverRepository;

    Reaction getReactionById(String reactionId) {
        return reactionDataReceiverRepository.findById(reactionId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    List<Reaction> getAllReactions() {
        return reactionDataReceiverRepository.findAll();
    }

    Reaction createReaction(ReactionRequest reactionRequest) {
        var reaction = Reaction.builder()
                .name(reactionRequest.name())
                .active(false)
                .archived(false)
                .build();
        return reactionDataReceiverRepository.save(reaction);
    }

    void updateReactionName(String id, ReactionUpdateRequest reactionUpdateRequest) {
        reactionDataReceiverRepository.updateReactionName(id, reactionUpdateRequest.name());
    }

    void updateReactionActivity(String reactionId, boolean active) {
        reactionDataReceiverRepository.updateReactionActive(reactionId, active);
    }

    void updateReactionArchived(String reactionId, boolean archived) {
        reactionDataReceiverRepository.updateReactionArchived(reactionId, archived);
    }
}

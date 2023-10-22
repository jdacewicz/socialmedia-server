package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

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
                .build();
        return reactionDataReceiverRepository.save(reaction);
    }
}
